package kr.co.F1FS.app.domain.elastic.application.port.in.bookmark;

import kr.co.F1FS.app.global.presentation.dto.bookmark.ResponseBookmarkDocumentDTO;
import org.springframework.data.domain.Page;

public interface BookmarkSearchUseCase {
    Page<ResponseBookmarkDocumentDTO> getBookmarkList(int page, int size, String condition, String keyword);
}
