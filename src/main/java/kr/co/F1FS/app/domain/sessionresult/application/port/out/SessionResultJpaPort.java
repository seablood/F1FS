package kr.co.F1FS.app.domain.sessionresult.application.port.out;

import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;

import java.util.List;

public interface SessionResultJpaPort {
    SessionResult save(SessionResult sessionResult);
    SessionResult saveAndFlush(SessionResult sessionResult);
    List<ResponseSessionResultListDTO> findAllBySession(Long sessionId);
}
