package kr.co.F1FS.app.global.config.redis;

import kr.co.F1FS.app.global.util.exception.redis.RedisException;
import kr.co.F1FS.app.global.util.exception.redis.RedisExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHandler {
    private final RedisConfig redisConfig;

    public ValueOperations<String, Object> getValueOperations(){ // 단일 데이터 접근
        return redisConfig.redisTemplate().opsForValue();
    }

    public void executeOperations(Runnable operations){
        try {
            operations.run();
        } catch (Exception e){
            throw new RedisException(RedisExceptionType.REDIS_TEMPLATE_ERROR);
        }
    }
}
