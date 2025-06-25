package kr.co.F1FS.app.domain.notification.application.service;

import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import kr.co.F1FS.app.global.util.exception.redis.RedisException;
import kr.co.F1FS.app.global.util.exception.redis.RedisExceptionType;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationRedisService {
    private final NotificationMapper notificationMapper;
    private final RedisHandler redisHandler;

    public void saveNotification(NotificationRedis redis, String key){
        Set<Object> subscribeList = redisHandler.getSetOperations().members(key);

        for (Object obj : subscribeList){
            Long userId = Long.valueOf(obj.toString());
            redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                    .leftPush("notification:"+userId, redis));
            redisHandler.getNotificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(3));
            redisHandler.getNotificationRedisListOperations().trim("notification:"+userId, 0, 30);
        }
    }

    public void saveNotificationForPersonal(NotificationRedis redis, User user){
        if(redis.getTopic().equals("like")){
            NotificationRedis likeRedis = getNotificationList(user).stream()
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

    public void saveNotificationForPersonal(NotificationRedis redis, Long userId){
        redisHandler.executeOperations(() -> redisHandler.getNotificationRedisListOperations()
                .leftPush("notification:"+userId, redis));
        redisHandler.getNotificationRedisTemplate().expire("notification:"+userId, Duration.ofDays(3));
        redisHandler.getNotificationRedisListOperations().trim("notification:"+userId, 0, 30);
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

    public void readNotification(User user, Long id){
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

    public void deleteNotification(User user, Long id){
        String key = "notification:"+user.getId();
        List<NotificationRedis> currentList = getNotificationList(user);
        List<NotificationRedis> updateList = currentList.stream()
                .filter(redis1 -> !Objects.equals(id, redis1.getId()))
                .toList();

        redisHandler.getNotificationRedisTemplate().delete(key);
        if (!updateList.isEmpty()) redisHandler.getNotificationRedisListOperations().rightPushAll(key, updateList);
    }

    public boolean isSubscribe(Long userId, String topic){
        return redisHandler.getSetOperations().isMember("topic:"+topic, userId);
    }

    public Page<ResponseNotificationRedisDTO> getNotificationRedisList(int page, int size, User user){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
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
