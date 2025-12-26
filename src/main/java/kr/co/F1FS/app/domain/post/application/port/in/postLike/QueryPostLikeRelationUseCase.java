package kr.co.F1FS.app.domain.post.application.port.in.postLike;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;

public interface QueryPostLikeRelationUseCase {
    PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post);
}
