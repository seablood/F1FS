package kr.co.F1FS.app.domain.suspend.application.port.in;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.domain.User;

public interface QuerySuspensionLogUseCase {
    SuspensionLog findBySuspendUser(User suspendUser);
}
