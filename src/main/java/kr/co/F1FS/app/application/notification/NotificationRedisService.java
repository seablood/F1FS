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
            redisConfig.notificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(3));
        }
    }

    public void saveNotificationForPersonal(NotificationRedis redis, Long userId){
        if(redis.getTopic().equals("like")){
            NotificationRedis likeRedis = getNotificationList(userId).stream()
                    .filter(redis1 -> redis1.getTopic().equals("like") && redis1.getContentId().equals(redis.getContentId()))
                    .findFirst()
                    .orElse(null);

            if(likeRedis != null){
                redis.setContent(redis.getContent()+"님 외 많은 분들이 게시글을 좋아합니다!");
                deleteNotification(userId, likeRedis);
            } else {
                redis.setContent(redis.getContent()+"님이 게시글을 좋아합니다!");
            }
        }

        redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                .leftPush("notification:"+userId, redis));
        redisConfig.notificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(3));
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

    public void deleteNotification(User user, NotificationRedis redis){
        String key = "notification:"+user.getId();

        redisHandler.getNotificationRedisListOperations().remove(key, 1, redis);
    }

    public boolean isSubscribe(Long userId, String topic){
        return redisHandler.getSetOperations().isMember("topic:"+topic, userId);
    }

    public List<NotificationRedis> getNotificationList(Long userId){
        String key = "notification:"+userId;

        return redisHandler.getNotificationRedisListOperations().range(key, 0, -1);
    }

    public void deleteNotification(Long userId, NotificationRedis redis){
        String key = "notification:"+userId;

        redisHandler.getNotificationRedisListOperations().remove(key, 1, redis);
    }
}
