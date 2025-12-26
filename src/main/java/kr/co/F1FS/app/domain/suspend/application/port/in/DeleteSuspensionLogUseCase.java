package kr.co.F1FS.app.domain.suspend.application.port.in;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;

public interface DeleteSuspensionLogUseCase {
    void delete(SuspensionLog log);
}
