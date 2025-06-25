package kr.co.F1FS.app.domain.admin.user.application.port.out;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;

public interface AdminUserSuspensionLogPort {
    void save(SuspensionLog log);
}
