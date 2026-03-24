package kr.co.F1FS.app.domain.suspend.infrastructure.adapter;

import kr.co.F1FS.app.domain.suspend.application.port.out.SuspensionLogJpaPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.infrastructure.repository.SuspensionLogRepository;
import kr.co.F1FS.app.domain.suspend.infrastructure.repository.dsl.SuspensionLogDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuspensionLogJpaAdapter implements SuspensionLogJpaPort {
    private final SuspensionLogRepository suspensionLogRepository;
    private final SuspensionLogDSLRepository suspensionLogDSLRepository;

    @Override
    public SuspensionLog save(SuspensionLog suspensionLog) {
        return suspensionLogRepository.save(suspensionLog);
    }

    @Override
    public SuspensionLog saveAndFlush(SuspensionLog suspensionLog) {
        return suspensionLogRepository.saveAndFlush(suspensionLog);
    }

    @Override
    public SuspensionLog findByUser(Long userId) {
        return suspensionLogDSLRepository.findNyUser(userId);
    }

    @Override
    public void delete(SuspensionLog suspensionLog) {
        suspensionLogRepository.delete(suspensionLog);
    }
}
