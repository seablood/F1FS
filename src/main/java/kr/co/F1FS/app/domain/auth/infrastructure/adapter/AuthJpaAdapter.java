package kr.co.F1FS.app.domain.auth.infrastructure.adapter;

import kr.co.F1FS.app.domain.auth.application.port.out.AuthJpaPort;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.infrastructure.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthJpaAdapter implements AuthJpaPort {
    private final VerificationCodeRepository codeRepository;

    @Override
    public VerificationCode save(VerificationCode code) {
        return codeRepository.save(code);
    }

    @Override
    public List<VerificationCode> findAll() {
        return codeRepository.findAll();
    }

    @Override
    public VerificationCode findVerificationCodeByEmailAndCode(String email, String code) {
        return codeRepository.findVerificationCodeByEmailAndCode(email, code)
                .orElseThrow(() -> new IllegalArgumentException("인증 오류"));
    }

    @Override
    public void delete(VerificationCode code) {
        codeRepository.delete(code);
    }
}
