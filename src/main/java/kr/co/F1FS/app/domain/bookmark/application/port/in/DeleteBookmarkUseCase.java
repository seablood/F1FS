package kr.co.F1FS.app.domain.bookmark.application.port.in;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;

public interface DeleteBookmarkUseCase {
    void delete(Bookmark bookmark);
}
