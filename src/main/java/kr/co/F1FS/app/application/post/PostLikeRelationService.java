package kr.co.F1FS.app.application.post;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.notification.FCMLiveService;
import kr.co.F1FS.app.application.notification.NotificationRedisService;
import kr.co.F1FS.app.domain.model.elastic.PostDocument;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.PostLikeRelation;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.elastic.PostSearchRepository;
import kr.co.F1FS.app.domain.repository.rdb.post.PostLikeRelationRepository;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeRelationService {
    private final PostLikeRelationRepository relationRepository;
    private final PostSearchRepository postSearchRepository;
    private final PostService postService;
    private final FCMLiveService fcmLiveService;
    private final NotificationRedisService redisService;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public void toggle(User user, Long id){
        Post post = postService.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingPost(post);

        PostDocument postDocument = postSearchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if(isLiked(user, post)){
            PostLikeRelation relation = relationRepository.findPostLikeRelationByUserAndPost(user, post);
            relationRepository.delete(relation);
            post.decreaseLike();
            postDocument.decreaseLikeNum();
        }
        else {
            PostLikeRelation relation = PostLikeRelation.builder()
                    .user(user)
                    .post(post)
                    .build();
            relationRepository.save(relation);
            post.increaseLike();
            postDocument.increaseLikeNum();

            if(redisService.isSubscribe(post.getAuthor().getId(), "like")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForLike(user);
                FCMNotification token = fcmUtil.getAuthorToken(post.getAuthor());
                fcmLiveService.sendPushForAuthor(pushDTO, token, id);
            }
        }

        postSearchRepository.save(postDocument);
    }

    public boolean isLiked(User user, Post post){
        return relationRepository.existsPostLikeRelationByUserAndPost(user, post);
    }
}
