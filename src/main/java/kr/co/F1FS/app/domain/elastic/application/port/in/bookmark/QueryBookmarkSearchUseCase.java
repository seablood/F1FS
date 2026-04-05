package kr.co.F1FS.app.domain.elastic.application.port.in.bookmark;

import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;

import java.util.List;

public interface QueryBookmarkSearchUseCase {
    BookmarkDocument findById(Long id);
    List<BookmarkDocument> findAllByPostId(Long postId);
}
