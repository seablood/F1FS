package kr.co.F1FS.app.domain.complain.post.application.port.in.admin;

import kr.co.F1FS.app.domain.complain.post.presentation.dto.admin.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostComplainUseCase {
    Page<AdminResponsePostComplainDTO> getAllComplain(int page, int size, String condition);
    ResponsePostComplainDTO getComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
