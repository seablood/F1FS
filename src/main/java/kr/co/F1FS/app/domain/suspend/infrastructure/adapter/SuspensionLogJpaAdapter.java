package kr.co.F1FS.app.domain.suspend.infrastructure.adapter;

import kr.co.F1FS.app.domain.suspend.application.port.out.SuspensionLogJpaPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.infrastructure.repository.SuspensionLogRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuspensionLogJpaAdapter implements SuspensionLogJpaPort {
    private final SuspensionLogRepository suspensionLogRepository;

    @Override
    public SuspensionLog save(SuspensionLog suspensionLog) {
        return suspensionLogRepository.save(suspensionLog);
    }

    @Override
    public SuspensionLog saveAndFlush(SuspensionLog suspensionLog) {
        return suspensionLogRepository.saveAndFlush(suspensionLog);
    }

    @Override
    public SuspensionLog findBySuspendUser(User user) {
        return suspensionLogRepository.findBySuspendUser(user)
                .orElseThrow(() -> new IllegalArgumentException("징계 이력이 없습니다."));
    }

    @Override
    public void delete(SuspensionLog suspensionLog) {
        suspensionLogRepository.delete(suspensionLog);
    }
}
