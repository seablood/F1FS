package kr.co.F1FS.app.domain.suspend.application.port.in;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;

public interface SuspensionLogUseCase {
    SuspensionLog save(SuspensionLog log);
    ResponseSuspensionLogDTO getSuspensionLog(User user);
    void deleteSuspensionLog(User user);
    SuspensionLog toSuspensionLog(CreateSuspensionLogCommand command, User user);
}
