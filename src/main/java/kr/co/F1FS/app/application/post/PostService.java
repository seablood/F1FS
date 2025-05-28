package kr.co.F1FS.app.application.post;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.SlackService;
import kr.co.F1FS.app.application.complain.post.PostComplainService;
import kr.co.F1FS.app.application.notification.FCMLiveService;
import kr.co.F1FS.app.application.notification.NotificationRedisService;
import kr.co.F1FS.app.application.search.PostSearchService;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.domain.model.rdb.PostComplain;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import kr.co.F1FS.app.presentation.post.dto.*;
import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.post.PostRepository;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.application.ValidationService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final PostSearchService postSearchService;
    private final ValidationService validationService;
    private final FCMLiveService fcmLiveService;
    private final NotificationRedisService redisService;
    private final PostComplainService complainService;
    private final SlackService slackService;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public Post save(CreatePostDTO dto, User author){
        Post post = CreatePostDTO.toEntity(dto, author);
        validationService.checkValid(post);
        postRepository.save(post);
        postSearchService.save(post);

        List<FCMNotification> tokens = fcmUtil.getFollowerToken(author).stream()
                .filter(token -> redisService.isSubscribe(token.getUserId(), "post"))
                .toList();
        if(!tokens.isEmpty()){
            FCMPushDTO pushDTO = fcmUtil.sendPushForPost(author, post.getTitle());
            fcmLiveService.sendPushForFollow(pushDTO, tokens, post.getId());
            log.info("팔로워 푸시 알림 전송 완료");
        }

        return postRepository.save(post);
    }

    @Transactional
    public void postComplain(Long id, User user, CreatePostComplainDTO dto){
        Post post = findByIdNotDTO(id);
        PostComplain complain = PostComplain.builder()
                .toPost(post)
                .fromUser(user)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();

        complainService.save(complain);
        sendMessage(complain);
        log.info("게시글 신고 완료 : {}", post.getTitle());
    }

    public Page<ResponseSimplePostDTO> findAll(int page, int size, String condition){
        Pageable pageable = conditionSwitch(page, size, condition);

        return postRepository.findAll(pageable).map(post -> ResponseSimplePostDTO.toDto(post));
    }


    @Cacheable(value = "PostDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostDTO findById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        return ResponsePostDTO.toDto(post);
    }

    @Cacheable(value = "PostNotDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public Post findByIdNotDTO(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Transactional
    public ResponsePostDTO modify(Long id, ModifyPostDTO dto, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        cacheEvictUtil.evictCachingPost(post);

        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_UPDATE_POST);
        }
        post.modify(dto);
        postRepository.saveAndFlush(post);
        return ResponsePostDTO.toDto(post);
    }

    @Transactional
    public void delete(Long id, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        cacheEvictUtil.evictCachingPost(post);

        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_DELETE_POST);
        }
        postRepository.delete(post);
    }

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

    public void sendMessage(PostComplain complain){
        String title = "게시글 신고가 접수되었습니다.";
        HashMap<String, String> data = new HashMap<>();
        data.put("신고자", complain.getFromUser().getNickname());
        data.put("신고된 게시글", complain.getToPost().getTitle());
        data.put("신고 사유", complain.getDescription());

        slackService.sendComplainMessage(title, data);
    }
}
