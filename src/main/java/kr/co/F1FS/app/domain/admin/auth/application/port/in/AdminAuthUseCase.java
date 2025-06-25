package kr.co.F1FS.app.domain.admin.auth.application.port.in;

import kr.co.F1FS.app.domain.admin.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.admin.auth.presentation.dto.ResponseUserDTO;

public interface AdminAuthUseCase {
    ResponseUserDTO save(CreateAdminUserDTO dto);
    void deleteVerificationCode();
    void markDormantAccounts();
}
