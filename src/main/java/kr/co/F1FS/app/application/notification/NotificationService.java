package kr.co.F1FS.app.application.notification;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.model.rdb.Notification;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.redis.NotificationRedis;
import kr.co.F1FS.app.domain.repository.rdb.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRedisService redisService;
    private final NotificationRepository notificationRepository;

    private static final String TOPIC_PREFIX = "topic:";

    @Transactional
    public void saveNotification(User user, Long notificationId){
        NotificationRedis redis = redisService.getNotificationList(user).stream()
                .filter(redis1 -> notificationId.equals(redis1.getId()))
                .findFirst()
                .orElse(null);

        if(redis != null){
            Notification notification = Notification.builder().redis(redis).build();
            redisService.deleteNotification(user, redis);
            notificationRepository.save(notification);
        }
    }

    public void saveSubscribeTopic(User user, String topic){
        String key = TOPIC_PREFIX+topic;
        redisService.saveSubscribe(user, key);
    }

    public void saveUnSubscribeTopic(User user, String topic){
        String key = TOPIC_PREFIX+topic;
        redisService.saveUnsubscribe(user, key);
    }
}
