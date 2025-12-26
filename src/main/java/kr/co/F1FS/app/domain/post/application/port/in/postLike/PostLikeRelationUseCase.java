package kr.co.F1FS.app.domain.post.application.port.in.postLike;

import kr.co.F1FS.app.domain.user.domain.User;

public interface PostLikeRelationUseCase {
    void toggle(User user, Long id);
}
