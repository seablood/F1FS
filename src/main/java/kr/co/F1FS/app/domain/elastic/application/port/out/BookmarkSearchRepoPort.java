package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;

import java.util.List;

public interface BookmarkSearchRepoPort {
    void save(BookmarkDocument bookmarkDocument);
    BookmarkDocument findById(Long id);
    List<BookmarkDocument> findAllByPostId(Long postId);
    void delete(BookmarkDocument bookmarkDocument);
}
