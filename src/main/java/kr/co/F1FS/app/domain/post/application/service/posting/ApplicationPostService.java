package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.CreatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.DeletePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.QueryPostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.UpdatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.service.bookmark.ModifyBookmarkSearchAsyncService;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.file.application.port.in.DeleteUploadFileUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.post.application.mapper.posting.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.posting.*;
import kr.co.F1FS.app.domain.post.domain.PostBlock;
import kr.co.F1FS.app.domain.post.presentation.dto.*;
import kr.co.F1FS.app.domain.tag.application.port.in.tag.CreateTagUseCase;
import kr.co.F1FS.app.domain.tag.application.port.in.tag.QueryTagUseCase;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.PostBlockType;
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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final CreateTagUseCase createTagUseCase;
    private final QueryTagUseCase queryTagUseCase;
    private final DeleteUploadFileUseCase deleteUploadFileUseCase;
    private final ModifyBookmarkSearchAsyncService modifyBookmarkSearchAsyncService;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public ResponsePostDTO save(CreatePostBlockRequestDTO requestDTO, User author) {
        Post post = createPostUseCase.save(requestDTO, author);
        List<String> tags = requestDTO.getTags();
        if(!tags.isEmpty()) {
            List<Tag> tagList = queryTagUseCase.saveTagList(tags);
            createTagUseCase.save(post, tagList);
        }

        createPostSearchUseCase.save(post, tags);

        fcmLiveUseCase.sendPushAfterPosting(post, author);

        return postMapper.toResponsePostDTO(post, tags);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostListDTO> getPostAll(int page, int size, String condition){
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostUseCase.findPostListForDTO(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostListDTO> getPostListByUser(int page, int size, String condition, User user) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostUseCase.findAllByAuthorForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "PostDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostDTO getPostById(Long id){
        Post post = queryPostUseCase.findByIdWithJoin(id);
        PostDocument document = queryPostSearchUseCase.findById(id);

        return postMapper.toResponsePostDTO(post, document.getTags());
    }

    @Override
    @Transactional
    public ResponsePostDTO modify(Long id, ModifyPostBlockRequestDTO requestDTO, User user) {
        Post post = queryPostUseCase.findByIdWithJoin(id);
        Set<String> oldFileUrls = post.getBlocks().stream()
                .filter(block -> (block.getBlockType() == PostBlockType.IMAGE) || (block.getBlockType() == PostBlockType.VIDEO))
                .map(PostBlock::getContent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<String> newFileUrls = requestDTO.getBlocks().stream()
                .filter(block -> (block.getType() == PostBlockType.IMAGE) || (block.getType() == PostBlockType.VIDEO))
                .map(ModifyPostBlockRequestDTO.BlockRequest::getContent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<String> deleteTargets = new HashSet<>(oldFileUrls);
        deleteTargets.removeAll(newFileUrls);

        PostDocument document = queryPostSearchUseCase.findById(id);
        List<String> addTags = requestDTO.getAddTags();
        List<String> deleteTags = requestDTO.getDeleteTags();

        updatePostUseCase.modify(requestDTO, post, user);
        updatePostSearchUseCase.modify(document, post, addTags, deleteTags);
        modifyBookmarkSearchAsyncService.addDTO(post.getId(), post.getTitle());

        registerAfterCommit(() -> deleteUploadFileUseCase.deleteFile(deleteTargets));

        return postMapper.toResponsePostDTO(post, addTags);
    }

    @Override
    @Transactional
    public void delete(Long id, User user){
        Post post = queryPostUseCase.findById(id);
        PostDocument document = queryPostSearchUseCase.findById(id);

        deletePostUseCase.delete(post, user);
        deletePostSearchUseCase.delete(document);
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

    private void registerAfterCommit(Runnable task){
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        task.run();
                    }
                }
        );
    }
}
