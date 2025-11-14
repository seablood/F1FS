package kr.co.F1FS.app.domain.post.application.service;

import kr.co.F1FS.app.domain.elastic.application.port.in.PostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.PostLikeRelationUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.PostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.PostJpaPort;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeRelationJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeRelationService implements PostLikeRelationUseCase {
    private final PostLikeRelationJpaPort relationJpaPort;
    private final PostJpaPort postJpaPort;
    private final PostSearchUseCase searchUseCase;
    private final PostUseCase postUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final NotificationRedisUseCase redisUseCase;
    private final PostMapper postMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Override
    @Transactional
    public void toggle(User user, Long id){
        Post post = postJpaPort.findById(id);
        cacheEvictUtil.evictCachingPost(post);

        PostDocument postDocument = searchUseCase.findById(id);

        if(isLiked(user, post)){
            PostLikeRelation relation = relationJpaPort.findPostLikeRelationByUserAndPost(user, post);
            relationJpaPort.delete(relation);
            postUseCase.decreaseLike(post);
            searchUseCase.decreaseLikeNum(postDocument);
        }
        else {
            PostLikeRelation relation = postMapper.toPostLikeRelation(user, post);
            relationJpaPort.save(relation);
            postUseCase.increaseLike(post);
            searchUseCase.increaseLikeNum(postDocument);

            pushNotification(user, post, id);
        }

        searchUseCase.save(postDocument);
    }

    @Override
    public boolean isLiked(User user, Post post){
        return relationJpaPort.existsPostLikeRelationByUserAndPost(user, post);
    }

    @Override
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
