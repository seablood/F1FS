package kr.co.F1FS.app.domain.bookmark.application.port.in;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryBookmarkUseCase {
    Page<ResponseBookmarkListDTO> findBookmarkListForDTO(Long userId, Pageable pageable);
    Bookmark findByPostAndUser(Post post, User user);
}
