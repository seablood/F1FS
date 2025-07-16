package kr.co.F1FS.app.domain.session.infrastructure.adapter;

import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.session.infrastructure.repository.SessionRepository;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultSessionPort;
import kr.co.F1FS.app.global.util.exception.session.SessionException;
import kr.co.F1FS.app.global.util.exception.session.SessionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionResultSessionAdapter implements SessionResultSessionPort {
    private final SessionRepository sessionRepository;

    @Override
    public Session getSessionByIdNotDTO(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new SessionException(SessionExceptionType.SESSION_NOT_FOUND));
    }
}
