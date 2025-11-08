package kr.co.F1FS.app.domain.suspend.application.port.out;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.domain.User;

public interface SuspensionLogJpaPort {
    SuspensionLog save(SuspensionLog suspensionLog);
    SuspensionLog saveAndFlush(SuspensionLog suspensionLog);
    SuspensionLog findBySuspendUser(User user);
    void delete(SuspensionLog suspensionLog);
}
