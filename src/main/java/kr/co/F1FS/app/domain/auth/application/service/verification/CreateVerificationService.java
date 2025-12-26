package kr.co.F1FS.app.domain.auth.application.service.verification;

import kr.co.F1FS.app.domain.auth.application.port.in.verification.CreateVerificationUseCase;
import kr.co.F1FS.app.domain.auth.application.port.out.AuthJpaPort;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateVerificationService implements CreateVerificationUseCase {
    private final AuthJpaPort authJpaPort;
    private final VerificationDomainService verificationDomainService;

    @Override
    @Transactional
    public VerificationCode createCode(User user) {
        String code = verificationDomainService.generateVerificationCode();
        VerificationCode verificationCode = verificationDomainService.createEntity(user, code);

        return authJpaPort.save(verificationCode);
    }

    @Override
    public VerificationCode createCode(ResponseUserDTO dto) {
        String code = verificationDomainService.generateVerificationCode();
        VerificationCode verificationCode = verificationDomainService.createEntity(dto, code);

        return authJpaPort.save(verificationCode);
    }
}
