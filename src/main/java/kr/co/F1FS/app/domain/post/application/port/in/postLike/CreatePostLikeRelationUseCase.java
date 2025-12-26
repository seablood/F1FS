package kr.co.F1FS.app.domain.post.application.port.in.postLike;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreatePostLikeRelationUseCase {
    void save(User user, Post post);
}
