package kr.co.F1FS.app.domain.suspend.application.port.in.admin;

import kr.co.F1FS.app.domain.suspend.presentation.dto.admin.SuspendRequestDTO;

public interface AdminSuspensionLogUseCase {
    void setSuspend(SuspendRequestDTO dto);
}
