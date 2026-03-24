package kr.co.F1FS.app.domain.bookmark.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkDSLRepository {
    Page<ResponseBookmarkListDTO> findBookmarkList(Long userId, Pageable pageable);
}
