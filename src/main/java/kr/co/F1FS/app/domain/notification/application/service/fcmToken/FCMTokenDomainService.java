package kr.co.F1FS.app.domain.notification.application.service.fcmToken;

import kr.co.F1FS.app.domain.notification.application.mapper.fcmToken.FCMTokenMapper;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMTokenDomainService {
    private final FCMTokenMapper fcmTokenMapper;

    public FCMToken createEntity(User user, String token){
        return fcmTokenMapper.toFCMToken(user, token);
    }
}
