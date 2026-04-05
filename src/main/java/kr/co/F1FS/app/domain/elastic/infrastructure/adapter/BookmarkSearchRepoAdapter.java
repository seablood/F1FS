package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.BookmarkSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.BookmarkSearchRepository;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkException;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkSearchRepoAdapter implements BookmarkSearchRepoPort {
    private final BookmarkSearchRepository bookmarkSearchRepository;

    @Override
    public void save(BookmarkDocument bookmarkDocument) {
        bookmarkSearchRepository.save(bookmarkDocument);
    }

    @Override
    public BookmarkDocument findById(Long id) {
        return bookmarkSearchRepository.findById(id)
                .orElseThrow(() -> new BookmarkException(BookmarkExceptionType.BOOKMARK_NOT_FOUND));
    }

    @Override
    public List<BookmarkDocument> findAllByPostId(Long postId) {
        return bookmarkSearchRepository.findAllByPostId(postId);
    }

    @Override
    public void delete(BookmarkDocument bookmarkDocument) {
        bookmarkSearchRepository.delete(bookmarkDocument);
    }
}
