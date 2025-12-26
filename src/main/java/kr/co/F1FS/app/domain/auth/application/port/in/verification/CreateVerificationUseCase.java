package kr.co.F1FS.app.domain.auth.application.port.in.verification;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface CreateVerificationUseCase {
    VerificationCode createCode(User user);
    VerificationCode createCode(ResponseUserDTO dto);
}
