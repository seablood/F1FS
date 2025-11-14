package kr.co.F1FS.app.domain.post.application.service;

import kr.co.F1FS.app.domain.elastic.application.port.in.PostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.PostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.PostJpaPort;
import kr.co.F1FS.app.domain.post.presentation.dto.*;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements PostUseCase {
    private final PostJpaPort postJpaPort;
    private final PostSearchUseCase searchUseCase;
    private final ValidationService validationService;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final NotificationRedisUseCase redisUseCase;
    private final PostMapper postMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Override
    @Transactional
    public ResponsePostDTO save(CreatePostDTO dto, User author){
        Post post = postMapper.toPost(dto, author);
        validationService.checkValid(post);
        postJpaPort.save(post);
        searchUseCase.save(post);

        List<FCMToken> tokens = fcmUtil.getFollowerToken(author).stream()
                .filter(token -> redisUseCase.isSubscribe(token.getUserId(), "post"))
                .toList();
        if(!tokens.isEmpty()){
            FCMPushDTO pushDTO = fcmUtil.sendPushForPost(author, post.getTitle());
            fcmLiveUseCase.sendPushForFollow(pushDTO, tokens, post.getId());
            log.info("팔로워 푸시 알림 전송 완료");
        }

        return postMapper.toResponsePostDTO(postJpaPort.save(post));
    }

    @Override
    public Page<ResponseSimplePostDTO> findAll(int page, int size, String condition){
        Pageable pageable = conditionSwitch(page, size, condition);

        return postJpaPort.findAll(pageable);
    }

    @Override
    @Cacheable(value = "PostDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostDTO findById(Long id){
        Post post = postJpaPort.findById(id);
        return postMapper.toResponsePostDTO(post);
    }

    @Override
    @Cacheable(value = "PostNotDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public Post findByIdNotDTO(Long id){
        return postJpaPort.findById(id);
    }

    @Override
    public Post findByIdNotDTONotCache(Long id) {
        return postJpaPort.findById(id);
    }

    @Override
    public Page<Post> findAllByAuthor(User user, Pageable pageable) {
        return postJpaPort.findAllByAuthor(user, pageable);
    }

    @Override
    @Transactional
    public ResponsePostDTO modify(Long id, ModifyPostDTO dto, User user){
        Post post = postJpaPort.findById(id);
        PostDocument document = searchUseCase.findById(id);
        cacheEvictUtil.evictCachingPost(post);

        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_UPDATE_POST);
        }
        post.modify(dto);
        searchUseCase.modify(document, post);

        postJpaPort.saveAndFlush(post);
        searchUseCase.save(document);
        return postMapper.toResponsePostDTO(post);
    }

    @Override
    public void increaseLike(Post post){
        post.increaseLike();
        postJpaPort.saveAndFlush(post);
    }

    @Override
    public void decreaseLike(Post post){
        post.decreaseLike();
        postJpaPort.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void delete(Long id, User user){
        Post post = postJpaPort.findById(id);
        PostDocument document = searchUseCase.findById(id);
        cacheEvictUtil.evictCachingPost(post);

        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_DELETE_POST);
        }
        postJpaPort.delete(post);
        searchUseCase.delete(document);
    }

    @Override
    public void delete(Post post) {
        postJpaPort.delete(post);
    }

    @Override
    public ResponseSimplePostDTO toResponseSimplePostDTO(Post post) {
        return postMapper.toResponseSimplePostDTO(post);
    }

    @Override
    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            case "like" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeNum"));
            default:
                throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
        }
    }
}
