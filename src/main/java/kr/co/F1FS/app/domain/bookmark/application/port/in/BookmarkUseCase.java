package kr.co.F1FS.app.domain.bookmark.application.port.in;

import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;

public interface BookmarkUseCase {
    void save(Long postId, User user);
    Page<ResponseBookmarkListDTO> getBookmarkAllList(int page, int size, String condition, User user);
    void delete(Long postId, User user);
}
