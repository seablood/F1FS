package kr.co.F1FS.app.domain.admin.user.application.port.in;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.admin.user.presentation.dto.SuspendRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserUseCase {
    Page<AdminResponseUserComplainDTO> findAll(int page, int size, String condition);
    Page<AdminResponseUserComplainDTO> getComplainByUser(int page, int size, String condition, String search);
    void setSuspend(SuspendRequestDTO dto);
    Pageable switchCondition(int page, int size, String condition);
}
