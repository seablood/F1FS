package kr.co.F1FS.app.domain.notification.application.service.redis;

import com.google.firebase.messaging.FirebaseMessaging;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SubscribeNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.fcm.FCMTopicRequestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscribeNotificationRedisService implements SubscribeNotificationRedisUseCase {
    private final RedisHandler redisHandler;

    @Override
    public void saveSubscribe(FCMTopicRequestDTO dto, User user, String key, FCMToken token) {
        if(token != null){
            try{
                FirebaseMessaging.getInstance().subscribeToTopic(List.of(token.getToken()), dto.getTopic());
                redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(key, user.getId()));
                log.info("토픽 구독 지정 성공");
            } catch (Exception e){
                log.error("토픽 구독 지정 실패");
            }
        }else {
            redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(key, user.getId()));
        }
    }

    @Override
    public void saveUnsubscribe(FCMTopicRequestDTO dto, User user, String key, FCMToken token) {
        if(token != null){
            try{
                FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(token.getToken()), dto.getTopic());
                redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(key, user.getId()));
            } catch (Exception e){
                log.error("토픽 구독 해제 실패");
            }
        }else {
            redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(key, user.getId()));
        }
    }

    @Override
    public boolean isSubscribe(Long userId, String topic) {
        return redisHandler.getSetOperations().isMember("topic:"+topic, userId);
    }
}
