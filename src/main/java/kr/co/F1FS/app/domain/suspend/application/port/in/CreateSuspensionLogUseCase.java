package kr.co.F1FS.app.domain.suspend.application.port.in;

import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateSuspensionLogUseCase {
    void save(CreateSuspensionLogCommand command, User user);
}
