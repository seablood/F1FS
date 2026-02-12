package kr.co.F1FS.app.domain.notification.application.service.fcmToken;

import com.google.firebase.messaging.FirebaseMessaging;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SubscribeNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.DeleteFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.fcmToken.FCMTokenJpaPort;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.Topic;
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

    private static final Topic[] TOPIC_LIST = {Topic.NEWS, Topic.POST, Topic.REPLY, Topic.LIKE, Topic.NOTE};

    @Override
    public void delete(FCMToken fcmToken, User user) {
        for (Topic topic : TOPIC_LIST){
            if(subscribeNotificationRedisUseCase.isSubscribe(user.getId(), topic)){
                try {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(fcmToken.getToken()), topic.toString());
                    log.info("토픽 구독 해제 성공 : {}", topic);
                } catch (Exception e){
                    log.error("토픽 구독 해제 실패 : {}", topic);
                }
            }
        }

        fcmTokenJpaPort.delete(fcmToken);
    }
}
