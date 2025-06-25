package kr.co.F1FS.app.global.config.redis;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.util.exception.redis.RedisException;
import kr.co.F1FS.app.global.util.exception.redis.RedisExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHandler {
    @Qualifier("notificationRedisTemplate")
    private final RedisTemplate<String, NotificationRedis> notificationRedisTemplate;
    @Qualifier("redisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, NotificationRedis> getNotificationRedisTemplate() {
        return notificationRedisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate(){
        return redisTemplate;
    }

    public ValueOperations<String, Object> getValueOperations(){ // 단일 데이터 접근
        return redisTemplate.opsForValue();
    }

    public SetOperations<String, Object> getSetOperations(){
        return redisTemplate.opsForSet();
    }

    public ListOperations<String, NotificationRedis> getNotificationRedisListOperations(){
        return notificationRedisTemplate.opsForList();
    }

    public void executeOperations(Runnable operations){
        try {
            operations.run();
        } catch (Exception e){
            throw new RedisException(RedisExceptionType.REDIS_TEMPLATE_ERROR);
        }
    }
}
