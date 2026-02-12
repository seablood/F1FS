package kr.co.F1FS.app.domain.notification.application.service.redis;

import kr.co.F1FS.app.domain.notification.application.port.in.redis.UpdateNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateNotificationRedisService implements UpdateNotificationRedisUseCase {
    private final RedisHandler redisHandler;

    @Override
    public void readNotification(User user, Long id, List<NotificationRedis> list) {
        String key = "notification:"+user.getId();

        for (NotificationRedis redis : list){
            if(Objects.equals(redis.getId(), id)){
                redis.updateIsRead(true);
                break;
            }
        }
        redisHandler.getNotificationRedisTemplate().delete(key);
        redisHandler.getNotificationRedisListOperations().rightPushAll(key, list);
    }
}
