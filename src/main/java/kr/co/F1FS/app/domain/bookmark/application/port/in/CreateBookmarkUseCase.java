package kr.co.F1FS.app.domain.bookmark.application.port.in;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateBookmarkUseCase {
    void save(Post post, User user);
}
