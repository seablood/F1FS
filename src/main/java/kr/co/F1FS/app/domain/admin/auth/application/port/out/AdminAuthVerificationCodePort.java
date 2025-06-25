package kr.co.F1FS.app.domain.admin.auth.application.port.out;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;

import java.util.List;

public interface AdminAuthVerificationCodePort {
    List<VerificationCode> findAll();
    void delete(VerificationCode code);
}
