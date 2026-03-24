package kr.co.F1FS.app.domain.suspend.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;

public interface SuspensionLogDSLRepository {
    SuspensionLog findNyUser(Long userId);
}
