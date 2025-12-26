package kr.co.F1FS.app.domain.auth.application.port.in.auth.admin;

import kr.co.F1FS.app.domain.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface AdminAuthUseCase {
    ResponseUserDTO save(CreateAdminUserDTO dto);
}
