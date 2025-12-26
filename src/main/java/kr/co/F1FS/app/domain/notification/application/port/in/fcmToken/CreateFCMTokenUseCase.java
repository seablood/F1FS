package kr.co.F1FS.app.domain.notification.application.port.in.fcmToken;

import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateFCMTokenUseCase {
    void save(User user, String token);
}
