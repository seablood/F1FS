package kr.co.F1FS.app.application.notification;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.model.rdb.Notification;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.redis.NotificationRedis;
import kr.co.F1FS.app.domain.repository.rdb.notification.NotificationRepository;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRedisService redisService;
    private final NotificationRepository notificationRepository;
    private final RedisHandler redisHandler;

    private static final String PREFIX = "notification:";
    private static final String TOPIC_PREFIX = "topic:";

    @Transactional
    public void saveNotification(User user, Long notificationId){
        String key = PREFIX+notificationId;
        List<NotificationRedis> redisList = redisHandler.getNotificationRedisListOperations()
                .range(key, 0, -1);

        for(NotificationRedis redis : redisList){
            if(redis.getId().equals(notificationId)){
                Notification notification = Notification.builder().redis(redis).build();
                redisHandler.getNotificationRedisListOperations().remove(PREFIX+user.getId(), 1, redis);
                notificationRepository.save(notification);
            }
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
