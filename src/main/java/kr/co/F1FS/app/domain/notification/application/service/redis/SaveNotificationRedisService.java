package kr.co.F1FS.app.domain.notification.application.service.redis;

import kr.co.F1FS.app.domain.notification.application.port.in.redis.FindNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SaveNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import kr.co.F1FS.app.global.util.exception.redis.RedisException;
import kr.co.F1FS.app.global.util.exception.redis.RedisExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SaveNotificationRedisService implements SaveNotificationRedisUseCase {
    private final FindNotificationRedisUseCase findNotificationRedisUseCase;
    private final RedisHandler redisHandler;

    @Override
    public void saveNotification(NotificationRedis redis, String key) {
        Set<Object> subscribeList = redisHandler.getSetOperations().members(key);

        for (Object obj : subscribeList){
            Long userId = Long.valueOf(obj.toString());
            redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                    .leftPush("notification:"+userId, redis));
            redisHandler.getNotificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(3));
            redisHandler.getNotificationRedisListOperations().trim("notification:"+userId, 0, 30);
        }
    }

    @Override
    public void saveNotificationForPersonal(NotificationRedis redis, User user) {
        if(redis.getTopic().equals("like")){
            NotificationRedis likeRedis = findNotificationRedisUseCase.getNotificationList(user).stream()
                    .filter(redis1 -> redis1.getTopic().equals("like") && redis1.getContentId().equals(redis.getContentId()))
                    .findFirst()
                    .orElseThrow(() -> new RedisException(RedisExceptionType.REDIS_TEMPLATE_ERROR));

            if(likeRedis.getContent().contains("많은 분들이")){
                return;
            } else {
                redis.setContent(redis.getContent()+"님 외 많은 분들이 게시글을 좋아합니다!");
            }
        }

        redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                .leftPush("notification:"+user.getId(), redis));
        redisHandler.getNotificationRedisTemplate().expire("notification:"+user.getId(), Duration.ofDays(3));
        redisHandler.getNotificationRedisListOperations().trim("notification:"+user.getId(), 0, 30);
    }

    @Override
    public void saveNotificationForPersonal(NotificationRedis redis, Long userId) {
        redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                .leftPush("notification:"+userId, redis));
        redisHandler.getNotificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(3));
        redisHandler.getNotificationRedisListOperations().trim("notification:"+userId, 0, 30);
    }
}
