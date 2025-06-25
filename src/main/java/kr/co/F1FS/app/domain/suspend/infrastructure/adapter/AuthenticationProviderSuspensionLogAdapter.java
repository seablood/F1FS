package kr.co.F1FS.app.domain.suspend.infrastructure.adapter;

import kr.co.F1FS.app.domain.suspend.application.service.SuspensionLogService;
import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.port.out.AuthenticationProviderSuspensionLogPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderSuspensionLogAdapter implements AuthenticationProviderSuspensionLogPort {
    private final SuspensionLogService logService;

    @Override
    public ResponseSuspensionLogDTO getSuspensionLog(User user) {
        return logService.getSuspensionLog(user);
    }

    @Override
    public void deleteSuspensionLog(User user) {
        logService.deleteSuspensionLog(user);
    }
}
