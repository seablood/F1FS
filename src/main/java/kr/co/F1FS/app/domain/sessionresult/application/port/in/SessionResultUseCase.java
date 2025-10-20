package kr.co.F1FS.app.domain.sessionresult.application.port.in;

import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;

import java.util.List;

public interface SessionResultUseCase {
    void save(List<CreateSessionResultCommand> commandList, Long id, String racingClassCode);
    List<ResponseSessionResultDTO> getSessionResultBySession(Session session);
}
