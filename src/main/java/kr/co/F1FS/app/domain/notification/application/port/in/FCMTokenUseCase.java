package kr.co.F1FS.app.domain.notification.application.port.in;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;

public interface FCMTokenUseCase {
    void save(User user, String token);
    FCMToken findByUserIdOrNull(Long userId);
    void deleteToken(User user);
}
