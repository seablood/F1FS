package kr.co.F1FS.app.domain.auth.application.port.in.verification;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;

public interface VerifyAndDeleteVerificationUseCase {
    void verifyCode(String email, String code);
    void delete(VerificationCode code);
}
