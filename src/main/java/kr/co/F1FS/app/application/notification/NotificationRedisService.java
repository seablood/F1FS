package kr.co.F1FS.app.application.notification;

import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.redis.NotificationRedis;
import kr.co.F1FS.app.global.config.redis.RedisConfig;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationRedisService {
    private final RedisHandler redisHandler;
    private final RedisConfig redisConfig;

    public void saveNotification(NotificationRedis redis, String key){
        Set<Object> subscribeList = redisHandler.getSetOperations().members(key);

        for (Object obj : subscribeList){
            Long userId = Long.valueOf(obj.toString());
            redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                    .leftPush("notification:"+userId, redis));
            redisConfig.notificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(7));
        }
    }

    public void saveSubscribe(User user, String key){
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().add(key, user.getId()));
    }

    public void saveUnsubscribe(User user, String key){
        redisHandler.executeOperations(() -> redisHandler.getSetOperations().remove(key, user.getId()));
    }

    public List<NotificationRedis> getNotificationList(User user){
        String key = "notification:"+user.getId();

        return redisHandler.getNotificationRedisListOperations().range(key, 0, -1);
    }

    public boolean isSubscribe(User user, String topic){
        return redisHandler.getSetOperations().isMember("topic:"+topic, user.getId());
    }
}
