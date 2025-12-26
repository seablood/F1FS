package kr.co.F1FS.app.domain.suspend.application.service;

import kr.co.F1FS.app.domain.suspend.application.port.in.CreateSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.out.SuspensionLogJpaPort;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSuspensionLogService implements CreateSuspensionLogUseCase {
    private final SuspensionLogJpaPort suspensionLogJpaPort;
    private final SuspensionLogDomainService suspensionLogDomainService;

    @Override
    public void save(CreateSuspensionLogCommand command, User user) {
        SuspensionLog log = suspensionLogDomainService.createEntity(command, user);

        suspensionLogJpaPort.save(log);
    }
}
