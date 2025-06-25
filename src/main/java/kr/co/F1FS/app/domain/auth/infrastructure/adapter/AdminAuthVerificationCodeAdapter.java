package kr.co.F1FS.app.domain.auth.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.auth.application.port.out.AdminAuthVerificationCodePort;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.infrastructure.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminAuthVerificationCodeAdapter implements AdminAuthVerificationCodePort {
    private final VerificationCodeRepository codeRepository;

    @Override
    public List<VerificationCode> findAll() {
        return codeRepository.findAll();
    }

    @Override
    public void delete(VerificationCode code) {
        codeRepository.delete(code);
    }
}
