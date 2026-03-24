package kr.co.F1FS.app.domain.suspend.application.port.in;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;

public interface QuerySuspensionLogUseCase {
    SuspensionLog findByUser(Long userId);
}
