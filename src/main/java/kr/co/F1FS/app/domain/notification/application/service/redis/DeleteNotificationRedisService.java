package kr.co.F1FS.app.domain.notification.application.service.redis;

import kr.co.F1FS.app.domain.notification.application.port.in.redis.DeleteNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.FindNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeleteNotificationRedisService implements DeleteNotificationRedisUseCase {
    private final FindNotificationRedisUseCase findNotificationRedisUseCase;
    private final RedisHandler redisHandler;

    @Override
    public void deleteNotification(User user, Long id, String key) {
        List<NotificationRedis> currentList = findNotificationRedisUseCase.getNotificationList(user);
        List<NotificationRedis> updateList = currentList.stream()
                .filter(redis1 -> !Objects.equals(id, redis1.getId()))
                .toList();

        redisHandler.getNotificationRedisTemplate().delete(key);
        if (!updateList.isEmpty()) redisHandler.getNotificationRedisListOperations().rightPushAll(key, updateList);
    }
}
