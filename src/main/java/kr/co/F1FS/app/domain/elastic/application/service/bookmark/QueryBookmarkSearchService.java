package kr.co.F1FS.app.domain.elastic.application.service.bookmark;

import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.QueryBookmarkSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.BookmarkSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.BookmarkSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryBookmarkSearchService implements QueryBookmarkSearchUseCase {
    private final BookmarkSearchRepository bookmarkSearchRepository;
    private final BookmarkSearchRepoPort bookmarkSearchRepoPort;

    @Override
    public BookmarkDocument findById(Long id) {
        return bookmarkSearchRepoPort.findById(id);
    }

    @Override
    public List<BookmarkDocument> findAllByPostId(Long postId) {
        return bookmarkSearchRepoPort.findAllByPostId(postId);
    }
}
