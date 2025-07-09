package kr.co.F1FS.app.domain.post.application.service;

import kr.co.F1FS.app.domain.elastic.application.port.in.PostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.PostLikeRelationUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeSearchPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostLikeRelationRepository;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeRelationService implements PostLikeRelationUseCase {
    private final PostLikeRelationRepository relationRepository;
    private final PostRepository postRepository;
    private final PostSearchUseCase searchUseCase;
    private final PostLikeSearchPort searchPort;
    private final PostService postService;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final NotificationRedisUseCase redisUseCase;
    private final PostMapper postMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public void toggle(User user, Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        cacheEvictUtil.evictCachingPost(post);

        PostDocument postDocument = searchPort.findById(id);

        if(isLiked(user, post)){
            PostLikeRelation relation = relationRepository.findPostLikeRelationByUserAndPost(user, post);
            relationRepository.delete(relation);
            postService.decreaseLike(post);
            searchUseCase.decreaseLikeNum(postDocument);
        }
        else {
            PostLikeRelation relation = postMapper.toPostLikeRelation(user, post);
            relationRepository.save(relation);
            postService.increaseLike(post);
            searchUseCase.increaseLikeNum(postDocument);

            pushNotification(user, post, id);
        }

        postRepository.saveAndFlush(post);
        searchPort.save(postDocument);
    }

    public boolean isLiked(User user, Post post){
        return relationRepository.existsPostLikeRelationByUserAndPost(user, post);
    }

    public void pushNotification(User user, Post post, Long id){
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            if(redisUseCase.isSubscribe(post.getAuthor().getId(), "like")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForLike(user);
                FCMToken token = fcmUtil.getAuthorToken(post.getAuthor());
                fcmLiveUseCase.sendPushForAuthor(pushDTO, token, post.getAuthor(), id);
            }
        }
    }
}
