package kr.co.F1FS.app.domain.bookmark.application.service;

import kr.co.F1FS.app.domain.bookmark.application.port.in.QueryBookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.application.port.out.BookmarkJpaPort;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryBookmarkService implements QueryBookmarkUseCase {
    private final BookmarkJpaPort bookmarkJpaPort;

    @Override
    public Page<ResponseBookmarkListDTO> findBookmarkListForDTO(Long userId, Pageable pageable) {
        return bookmarkJpaPort.findBookmarkList(userId, pageable);
    }

    @Override
    public Bookmark findByPostAndUser(Post post, User user) {
        return bookmarkJpaPort.findByPostAndUser(post, user);
    }
}
