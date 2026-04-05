package kr.co.F1FS.app.domain.elastic.application.service.bookmark;

import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.DeleteBookmarkSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.BookmarkSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.BookmarkSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookmarkSearchService implements DeleteBookmarkSearchUseCase {
    private final BookmarkSearchRepository bookmarkSearchRepository;
    private final BookmarkSearchRepoPort bookmarkSearchRepoPort;

    @Override
    public void delete(BookmarkDocument bookmarkDocument) {
        bookmarkSearchRepoPort.delete(bookmarkDocument);
    }
}
