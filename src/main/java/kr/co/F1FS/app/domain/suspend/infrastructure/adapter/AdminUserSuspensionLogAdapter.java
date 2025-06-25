package kr.co.F1FS.app.domain.suspend.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.user.application.port.out.AdminUserSuspensionLogPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.infrastructure.repository.SuspensionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserSuspensionLogAdapter implements AdminUserSuspensionLogPort {
    private final SuspensionLogRepository logRepository;

    @Override
    public void save(SuspensionLog log) {
        logRepository.save(log);
    }
}
