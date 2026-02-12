package kr.co.F1FS.app.domain.user.application.port.in.admin;

import kr.co.F1FS.app.domain.user.presentation.dto.admin.AdminResponseUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.SimpleResponseUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserUseCase {
    Page<SimpleResponseUserDTO> getUserAll(int page, int size, String condition);
    AdminResponseUserDTO getUserById(Long id);
    Pageable conditionSwitch(int page, int size, String condition);
}
