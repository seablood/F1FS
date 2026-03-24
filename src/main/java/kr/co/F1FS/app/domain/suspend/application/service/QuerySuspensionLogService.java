package kr.co.F1FS.app.domain.suspend.application.service;

import kr.co.F1FS.app.domain.suspend.application.port.in.QuerySuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.out.SuspensionLogJpaPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuerySuspensionLogService implements QuerySuspensionLogUseCase {
    private final SuspensionLogJpaPort suspensionLogJpaPort;

    @Override
    public SuspensionLog findByUser(Long userId) {
        return suspensionLogJpaPort.findByUser(userId);
    }
}
