package kr.co.F1FS.app.domain.notification.application.service.fcmToken;

import com.google.firebase.messaging.FirebaseMessaging;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SubscribeNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.DeleteFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.fcmToken.FCMTokenJpaPort;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteFCMTokenService implements DeleteFCMTokenUseCase {
    private final FCMTokenJpaPort fcmTokenJpaPort;
    private final SubscribeNotificationRedisUseCase subscribeNotificationRedisUseCase;

    private static final String[] TOPIC_LIST = {"news", "post", "reply", "like", "note"};

    @Override
    public void delete(FCMToken fcmToken, User user) {
        for (String topic : TOPIC_LIST){
            if(subscribeNotificationRedisUseCase.isSubscribe(user.getId(), topic)){
                try {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(fcmToken.getToken()), topic);
                    log.info("토픽 구독 해제 성공 : {}", topic);
                } catch (Exception e){
                    log.error("토픽 구독 해제 실패 : {}", topic);
                }
            }
        }

        fcmTokenJpaPort.delete(fcmToken);
    }
}
