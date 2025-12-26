package kr.co.F1FS.app.domain.auth.application.service.verification;

import kr.co.F1FS.app.domain.auth.application.port.in.verification.CheckVerificationUseCase;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckVerificationService implements CheckVerificationUseCase {
    private final VerificationDomainService verificationDomainService;

    @Override
    public boolean isExpired(VerificationCode code) {
        return verificationDomainService.isExpired(code);
    }
}
