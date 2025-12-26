package kr.co.F1FS.app.domain.email.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface EmailUseCase {
    void sendAuthEmail(User user, String code, String option);
    void sendCreateAccountEmail(ResponseUserDTO dto, String code);
    void sendUpdatePasswordEmail(User user, String code);
    void sendActiveAccountEmail(User user, String code);
}
