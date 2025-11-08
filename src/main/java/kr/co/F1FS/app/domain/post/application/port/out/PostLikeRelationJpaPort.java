package kr.co.F1FS.app.domain.post.application.port.out;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;

public interface PostLikeRelationJpaPort {
    PostLikeRelation save(PostLikeRelation relation);
    PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post);
    boolean existsPostLikeRelationByUserAndPost(User user, Post post);
    void delete(PostLikeRelation relation);
}
