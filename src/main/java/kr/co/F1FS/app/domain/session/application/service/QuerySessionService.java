package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.session.application.port.in.QuerySessionUseCase;
import kr.co.F1FS.app.domain.session.application.port.out.SessionJpaPort;
import kr.co.F1FS.app.domain.session.domain.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuerySessionService implements QuerySessionUseCase {
    private final SessionJpaPort sessionJpaPort;

    @Override
    public Session findById(Long id) {
        return sessionJpaPort.findById(id);
    }
}
