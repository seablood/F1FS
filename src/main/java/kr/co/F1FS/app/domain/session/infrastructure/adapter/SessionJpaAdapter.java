package kr.co.F1FS.app.domain.session.infrastructure.adapter;

import kr.co.F1FS.app.domain.session.application.port.out.SessionJpaPort;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.session.infrastructure.repository.SessionRepository;
import kr.co.F1FS.app.global.util.exception.session.SessionException;
import kr.co.F1FS.app.global.util.exception.session.SessionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionJpaAdapter implements SessionJpaPort {
    private final SessionRepository sessionRepository;

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session saveAndFlush(Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @Override
    public Session findById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new SessionException(SessionExceptionType.SESSION_NOT_FOUND));
    }
}
