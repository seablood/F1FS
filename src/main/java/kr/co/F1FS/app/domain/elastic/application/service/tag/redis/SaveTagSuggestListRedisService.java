package kr.co.F1FS.app.domain.elastic.application.service.tag.redis;

import kr.co.F1FS.app.domain.elastic.application.port.in.tag.redis.SaveTagSuggestListRedisUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveTagSuggestListRedisService implements SaveTagSuggestListRedisUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "tag-suggest:";

    @Override
    public boolean hasKey(String keyword) {
        return redisHandler.getStringListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<String> getSuggestList(String keyword) {
        return redisHandler.getStringListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveSuggestList(String keyword, List<String> tags) {
        redisHandler.getStringListRedisTemplate().opsForList().rightPushAll(KEY + keyword, tags);
        redisHandler.getStringListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
