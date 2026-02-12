package kr.co.F1FS.app.domain.auth.application.service.verification;

import kr.co.F1FS.app.domain.auth.application.port.in.verification.CreateVerificationUseCase;
import kr.co.F1FS.app.domain.auth.application.port.out.AuthJpaPort;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateVerificationService implements CreateVerificationUseCase {
    private final AuthJpaPort authJpaPort;
    private final VerificationDomainService verificationDomainService;

    @Override
    public VerificationCode createCode(String email) {
        String code = verificationDomainService.generateVerificationCode();
        VerificationCode verificationCode = verificationDomainService.createEntity(email, code);

        return authJpaPort.save(verificationCode);
    }
}
