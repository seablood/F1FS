package kr.co.F1FS.app.domain.bookmark.application.port.in;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateBookmarkUseCase {
    Bookmark save(Post post, User user);
}
