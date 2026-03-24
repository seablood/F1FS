package kr.co.F1FS.app.domain.bookmark.infrastructure.adapter;

import kr.co.F1FS.app.domain.bookmark.application.port.out.BookmarkJpaPort;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.bookmark.infrastructure.repository.BookmarkRepository;
import kr.co.F1FS.app.domain.bookmark.infrastructure.repository.dsl.BookmarkDSLRepository;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkException;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkJpaAdapter implements BookmarkJpaPort {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkDSLRepository bookmarkDSLRepository;

    @Override
    public void save(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    @Override
    public Page<ResponseBookmarkListDTO> findBookmarkList(Long userId, Pageable pageable) {
        return bookmarkDSLRepository.findBookmarkList(userId, pageable);
    }

    @Override
    public Bookmark findByPostAndUser(Post post, User user) {
        return bookmarkRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new BookmarkException(BookmarkExceptionType.BOOKMARK_NOT_FOUND));
    }

    @Override
    public void delete(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }
}
