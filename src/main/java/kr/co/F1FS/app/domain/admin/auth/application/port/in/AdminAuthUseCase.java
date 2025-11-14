package kr.co.F1FS.app.domain.admin.auth.application.port.in;

import kr.co.F1FS.app.domain.admin.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface AdminAuthUseCase {
    ResponseUserDTO save(CreateAdminUserDTO dto);
}
