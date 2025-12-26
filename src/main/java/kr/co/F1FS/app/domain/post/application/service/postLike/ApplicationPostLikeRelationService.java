package kr.co.F1FS.app.domain.post.application.service.postLike;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.QueryPostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.UpdatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.postLike.*;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.posting.UpdatePostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationPostLikeRelationService implements PostLikeRelationUseCase {
    private final CreatePostLikeRelationUseCase createPostLikeRelationUseCase;
    private final DeletePostLikeRelationUseCase deletePostLikeRelationUseCase;
    private final QueryPostLikeRelationUseCase queryPostLikeRelationUseCase;
    private final CheckPostLikeRelationUseCase checkPostLikeRelationUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final QueryPostUseCase queryPostUseCase;
    private final UpdatePostSearchUseCase updatePostSearchUseCase;
    private final QueryPostSearchUseCase queryPostSearchUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public void toggle(User user, Long id){
        Post post = queryPostUseCase.findById(id);
        PostDocument postDocument = queryPostSearchUseCase.findById(id);
        cacheEvictUtil.evictCachingPost(post);

        if(checkPostLikeRelationUseCase.existsPostLikeRelationByUserAndPost(user, post)){
            PostLikeRelation relation = queryPostLikeRelationUseCase.findPostLikeRelationByUserAndPost(user, post);
            deletePostLikeRelationUseCase.delete(relation);
            updatePostUseCase.decreaseLike(post);
            updatePostSearchUseCase.decreaseLikeNum(postDocument);
        }
        else {
            createPostLikeRelationUseCase.save(user, post);
            updatePostUseCase.increaseLike(post);
            updatePostSearchUseCase.increaseLikeNum(postDocument);

            fcmLiveUseCase.sendPushAfterPostLike(user, post, id);
        }
    }
}
