package kr.co.F1FS.app.domain.auth.application.service.verification;

import kr.co.F1FS.app.domain.auth.application.port.in.verification.QueryVerificationUseCase;
import kr.co.F1FS.app.domain.auth.application.port.out.AuthJpaPort;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryVerificationService implements QueryVerificationUseCase {
    private final AuthJpaPort authJpaPort;

    @Override
    public List<VerificationCode> findAll() {
        return authJpaPort.findAll();
    }
}
