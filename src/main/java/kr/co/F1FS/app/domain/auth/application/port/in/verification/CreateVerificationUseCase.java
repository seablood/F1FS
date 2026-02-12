package kr.co.F1FS.app.domain.auth.application.port.in.verification;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;

public interface CreateVerificationUseCase {
    VerificationCode createCode(String email);
}
