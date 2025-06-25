package kr.co.F1FS.app.domain.email.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;

public interface EmailUseCase {
    void sendAuthEmail(User user, String code, String option);
    void sendCreateAccountEmail(User user, String code);
    void sendUpdatePasswordEmail(User user, String code);
    void sendActiveAccountEmail(User user, String code);
}
