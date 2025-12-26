package kr.co.F1FS.app.domain.suspend.application.service;

import kr.co.F1FS.app.domain.suspend.application.port.in.DeleteSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.out.SuspensionLogJpaPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSuspensionLogService implements DeleteSuspensionLogUseCase {
    private final SuspensionLogJpaPort suspensionLogJpaPort;

    @Override
    public void delete(SuspensionLog log) {
        suspensionLogJpaPort.delete(log);
    }
}
