package kr.co.F1FS.app.domain.auth.application.port.out;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;

import java.util.List;

public interface AuthJpaPort {
    VerificationCode save(VerificationCode code);
    List<VerificationCode> findAll();
    VerificationCode findVerificationCodeByEmailAndCode(String email, String code);
    void delete(VerificationCode code);
}
