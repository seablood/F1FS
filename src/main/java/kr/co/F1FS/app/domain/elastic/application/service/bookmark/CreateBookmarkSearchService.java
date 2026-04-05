package kr.co.F1FS.app.domain.elastic.application.service.bookmark;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.CreateBookmarkSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.BookmarkSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.BookmarkSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBookmarkSearchService implements CreateBookmarkSearchUseCase {
    private final BookmarkSearchRepository bookmarkSearchRepository;
    private final BookmarkSearchRepoPort bookmarkSearchRepoPort;
    private final BookmarkSearchDocumentService bookmarkSearchDocumentService;

    @Override
    public void save(Bookmark bookmark) {
        BookmarkDocument bookmarkDocument = bookmarkSearchDocumentService.createEntity(bookmark);

        bookmarkSearchRepoPort.save(bookmarkDocument);
    }
}
