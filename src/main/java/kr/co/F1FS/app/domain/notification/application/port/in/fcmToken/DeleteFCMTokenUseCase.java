package kr.co.F1FS.app.domain.notification.application.port.in.fcmToken;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteFCMTokenUseCase {
    void delete(FCMToken fcmToken, User user);
}
