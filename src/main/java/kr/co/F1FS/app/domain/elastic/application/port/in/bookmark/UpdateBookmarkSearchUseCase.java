package kr.co.F1FS.app.domain.elastic.application.port.in.bookmark;

import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;

public interface UpdateBookmarkSearchUseCase {
    void modify(BookmarkDocument bookmarkDocument, String title);
}
