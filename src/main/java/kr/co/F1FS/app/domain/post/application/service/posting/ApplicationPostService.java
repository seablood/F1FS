package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.CreatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.DeletePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.QueryPostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.UpdatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.posting.*;
import kr.co.F1FS.app.domain.post.presentation.dto.*;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationPostService implements PostUseCase {
    private final CreatePostUseCase createPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final QueryPostUseCase queryPostUseCase;
    private final CreatePostSearchUseCase createPostSearchUseCase;
    private final UpdatePostSearchUseCase updatePostSearchUseCase;
    private final DeletePostSearchUseCase deletePostSearchUseCase;
    private final QueryPostSearchUseCase queryPostSearchUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public ResponsePostDTO save(CreatePostDTO dto, User author){
        Post post = createPostUseCase.save(dto, author);
        createPostSearchUseCase.save(post);

        fcmLiveUseCase.sendPushAfterPosting(post, author);

        return postMapper.toResponsePostDTO(post);
    }

    @Override
    public Page<ResponseSimplePostDTO> findAll(int page, int size, String condition){
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostUseCase.findAll(pageable);
    }

    @Override
    @Cacheable(value = "PostDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostDTO findById(Long id){
        return queryPostUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public ResponsePostDTO modify(Long id, ModifyPostDTO dto, User user){
        Post post = queryPostUseCase.findById(id);
        PostDocument document = queryPostSearchUseCase.findById(id);

        updatePostUseCase.modify(dto, post, user);
        updatePostSearchUseCase.modify(document, post);

        return postMapper.toResponsePostDTO(post);
    }

    @Override
    @Transactional
    public void delete(Long id, User user){
        Post post = queryPostUseCase.findById(id);
        PostDocument document = queryPostSearchUseCase.findById(id);

        deletePostUseCase.delete(post, user);
        deletePostSearchUseCase.delete(document);
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
