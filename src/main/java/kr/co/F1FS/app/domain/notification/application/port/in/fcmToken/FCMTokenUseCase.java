package kr.co.F1FS.app.domain.notification.application.port.in.fcmToken;

import kr.co.F1FS.app.domain.user.domain.User;

public interface FCMTokenUseCase {
    void save(User user, String token);
    void deleteToken(User user);
}
