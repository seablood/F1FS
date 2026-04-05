package kr.co.F1FS.app.domain.elastic.application.port.in.bookmark;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;

public interface CreateBookmarkSearchUseCase {
    void save(Bookmark bookmark);
}
