package kr.co.F1FS.app.domain.notification.application.mapper.fcmToken;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class FCMTokenMapper {
    public FCMToken toFCMToken(User user, String token){
        return FCMToken.builder()
                .userId(user.getId())
                .token(token)
                .build();
    }
}
