package kr.co.F1FS.app.domain.suspend.application.port.out;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;

public interface SuspensionLogJpaPort {
    SuspensionLog save(SuspensionLog suspensionLog);
    SuspensionLog saveAndFlush(SuspensionLog suspensionLog);
    SuspensionLog findByUser(Long userId);
    void delete(SuspensionLog suspensionLog);
}
