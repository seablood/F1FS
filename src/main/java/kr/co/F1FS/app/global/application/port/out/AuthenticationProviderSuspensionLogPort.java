package kr.co.F1FS.app.global.application.port.out;

import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface AuthenticationProviderSuspensionLogPort {
    ResponseSuspensionLogDTO getSuspensionLog(User user);
    void deleteSuspensionLog(User user);
}
