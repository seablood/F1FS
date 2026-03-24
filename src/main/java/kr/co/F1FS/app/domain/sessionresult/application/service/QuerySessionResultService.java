package kr.co.F1FS.app.domain.sessionresult.application.service;

import kr.co.F1FS.app.domain.sessionresult.application.port.in.QuerySessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultJpaPort;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuerySessionResultService implements QuerySessionResultUseCase {
    private final SessionResultJpaPort sessionResultJpaPort;

    @Override
    public List<ResponseSessionResultListDTO> findAllBySessionForDTO(Long sessionId) {
        return sessionResultJpaPort.findAllBySession(sessionId);
    }
}
