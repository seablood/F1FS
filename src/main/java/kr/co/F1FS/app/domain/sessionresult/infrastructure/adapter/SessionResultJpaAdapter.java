package kr.co.F1FS.app.domain.sessionresult.infrastructure.adapter;

import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultJpaPort;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.infrastructure.repository.SessionResultRepository;
import kr.co.F1FS.app.domain.sessionresult.infrastructure.repository.dsl.SessionResultDSLRepository;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionResultJpaAdapter implements SessionResultJpaPort {
    private final SessionResultRepository sessionResultRepository;
    private final SessionResultDSLRepository sessionResultDSLRepository;

    @Override
    public SessionResult save(SessionResult sessionResult) {
        return sessionResultRepository.save(sessionResult);
    }

    @Override
    public SessionResult saveAndFlush(SessionResult sessionResult) {
        return sessionResultRepository.saveAndFlush(sessionResult);
    }

    @Override
    public List<ResponseSessionResultListDTO> findAllBySession(Long sessionId) {
        return sessionResultDSLRepository.findAllBySession(sessionId);
    }
}
