package kr.co.F1FS.app.domain.sessionresult.application.port.in;

import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;

import java.util.List;

public interface CreateSessionResultUseCase {
    void save(List<CreateSessionResultCommand> commandList, Long id, String racingClassCode);
}
