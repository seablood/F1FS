package kr.co.F1FS.app.domain.complain.user.application.port.in.admin;

import kr.co.F1FS.app.domain.complain.user.presentation.dto.admin.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserComplainUseCase {
    Page<AdminResponseUserComplainDTO> findAll(int page, int size, String condition);
    Page<AdminResponseUserComplainDTO> getComplainByUser(int page, int size, String condition, String search);
    ResponseUserComplainDTO getComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
