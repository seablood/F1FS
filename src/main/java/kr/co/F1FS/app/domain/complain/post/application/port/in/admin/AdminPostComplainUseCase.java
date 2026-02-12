package kr.co.F1FS.app.domain.complain.post.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.post.SimpleResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostComplainUseCase {
    Page<SimpleResponsePostComplainDTO> getPostComplainAll(int page, int size, String condition);
    ResponsePostComplainDTO getPostComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
