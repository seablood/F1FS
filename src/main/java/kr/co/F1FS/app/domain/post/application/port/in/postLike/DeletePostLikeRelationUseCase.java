package kr.co.F1FS.app.domain.post.application.port.in.postLike;

import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;

public interface DeletePostLikeRelationUseCase {
    void delete(PostLikeRelation relation);
}
