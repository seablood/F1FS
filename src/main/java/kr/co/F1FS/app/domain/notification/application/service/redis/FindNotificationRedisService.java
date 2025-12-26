package kr.co.F1FS.app.domain.notification.application.service.redis;

import kr.co.F1FS.app.domain.notification.application.mapper.notice.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.FindNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FindNotificationRedisService implements FindNotificationRedisUseCase {
    private final NotificationMapper notificationMapper;
    private final RedisHandler redisHandler;

    @Override
    public List<NotificationRedis> getNotificationList(User user) {
        String key = "notification:"+user.getId();

        return redisHandler.getNotificationRedisListOperations().range(key, 0, -1);
    }

    @Override
    public void readNotification(User user, Long id) {
        String key = "notification:"+user.getId();
        List<NotificationRedis> list = getNotificationList(user);
        for (NotificationRedis redis : list){
            if(Objects.equals(redis.getId(), id)){
                redis.updateIsRead(true);
                break;
            }
        }

        redisHandler.getNotificationRedisTemplate().delete(key);
        redisHandler.getNotificationRedisListOperations().rightPushAll(key, list);
    }

    @Override
    public Page<ResponseNotificationRedisDTO> getNotificationRedisList(Pageable pageable, User user) {
        List<NotificationRedis> list = getNotificationList(user);

        Comparator<NotificationRedis> comparator = Comparator.comparing(NotificationRedis::getCreatedAt).reversed();
        List<ResponseNotificationRedisDTO> sorted = list.stream()
                .sorted(comparator)
                .map(redis -> notificationMapper.toResponseNotificationRedisDTO(redis))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<ResponseNotificationRedisDTO> paged = sorted.subList(start, end);

        return new PageImpl<>(paged, pageable, sorted.size());
    }
}
