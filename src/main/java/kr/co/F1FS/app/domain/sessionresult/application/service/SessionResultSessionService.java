package kr.co.F1FS.app.domain.sessionresult.application.service;

import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.mapper.SessionResultMapper;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.SessionResultSessionUseCase;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultJpaPort;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionResultSessionService implements SessionResultSessionUseCase {
    private final SessionResultJpaPort sessionResultJpaPort;
    private final SessionResultMapper sessionResultMapper;

    @Override
    public List<ResponseSessionResultDTO> getSessionResultBySession(Session session) {
        List<SessionResult> resultList = sessionResultJpaPort.findSessionResultsBySession(session);
        List<ResponseSessionResultDTO> dtoList = resultList.stream()
                .map(sessionResult -> sessionResultMapper.toResponseSessionResultDTO(sessionResult))
                .sorted(Comparator.comparingInt(ResponseSessionResultDTO::getPosition))
                .toList();

        return dtoList;
    }
}
