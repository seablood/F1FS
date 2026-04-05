package kr.co.F1FS.app.domain.bookmark.application.service;

import kr.co.F1FS.app.domain.bookmark.application.port.in.CreateBookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.application.port.out.BookmarkJpaPort;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBookmarkService implements CreateBookmarkUseCase {
    private final BookmarkJpaPort bookmarkJpaPort;
    private final BookmarkDomainService bookmarkDomainService;
    private final ValidationService validationService;

    @Override
    public Bookmark save(Post post, User user) {
        Bookmark bookmark = bookmarkDomainService.createEntity(post, user);
        validationService.checkValid(bookmark);

        return bookmarkJpaPort.save(bookmark);
    }
}
