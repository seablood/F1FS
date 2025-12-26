package kr.co.F1FS.app.domain.auth.application.port.in.verification;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;

import java.util.List;

public interface QueryVerificationUseCase {
    List<VerificationCode> findAll();
}
