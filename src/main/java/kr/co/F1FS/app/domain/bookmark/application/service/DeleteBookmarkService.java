package kr.co.F1FS.app.domain.bookmark.application.service;

import kr.co.F1FS.app.domain.bookmark.application.port.in.DeleteBookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.application.port.out.BookmarkJpaPort;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookmarkService implements DeleteBookmarkUseCase {
    private final BookmarkJpaPort bookmarkJpaPort;

    @Override
    public void delete(Bookmark bookmark) {
        bookmarkJpaPort.delete(bookmark);
    }
}
