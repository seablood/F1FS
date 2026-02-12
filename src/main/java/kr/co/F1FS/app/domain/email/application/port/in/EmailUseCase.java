package kr.co.F1FS.app.domain.email.application.port.in;

import kr.co.F1FS.app.domain.auth.presentation.dto.AuthorizationUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface EmailUseCase {
    void sendCreateAccountEmail(String email, String nickname);
    void sendUpdatePasswordEmail(User user);
    void sendActiveAccountEmail(AuthorizationUserDTO dto);
}
