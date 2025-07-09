package kr.co.F1FS.app.domain.post.application.port.in;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;

public interface PostLikeRelationUseCase {
    void toggle(User user, Long id);
    boolean isLiked(User user, Post post);
    void pushNotification(User user, Post post, Long id);
}
