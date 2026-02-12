package kr.co.F1FS.app.global.application.port.in;

import kr.co.F1FS.app.global.config.auth.PrincipalDetails;

public interface CheckAccountStatusUseCase {
    void checkAccount(PrincipalDetails principalDetails);
}
