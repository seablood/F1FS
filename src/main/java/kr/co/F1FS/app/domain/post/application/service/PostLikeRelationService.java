package kr.co.F1FS.app.domain.post.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeFCMLivePort;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeNotificationRedisPort;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeSearchPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostLikeRelationRepository;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeRelationService {
    private final PostLikeRelationRepository relationRepository;
    private final PostLikeSearchPort searchPort;
    private final PostService postService;
    private final PostLikeFCMLivePort fcmLivePort;
    private final PostLikeNotificationRedisPort redisPort;
    private final PostMapper postMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public void toggle(User user, Long id){
        Post post = postService.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingPost(post);

        PostDocument postDocument = searchPort.findById(id);

        if(isLiked(user, post)){
            PostLikeRelation relation = relationRepository.findPostLikeRelationByUserAndPost(user, post);
            relationRepository.delete(relation);
            postService.decreaseLike(post);
            searchPort.decreaseLikeNum(postDocument);
        }
        else {
            PostLikeRelation relation = postMapper.toPostLikeRelation(user, post);
            relationRepository.save(relation);
            postService.increaseLike(post);
            searchPort.increaseLikeNum(postDocument);

            pushNotification(user, post, id);
        }

        searchPort.save(postDocument);
    }

    public boolean isLiked(User user, Post post){
        return relationRepository.existsPostLikeRelationByUserAndPost(user, post);
    }

    public void pushNotification(User user, Post post, Long id){
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            if(redisPort.isSubscribe(post.getAuthor().getId(), "like")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForLike(user);
                FCMToken token = fcmUtil.getAuthorToken(post.getAuthor());
                fcmLivePort.sendPushForAuthor(pushDTO, token, post.getAuthor(), id);
            }
        }
    }
}
