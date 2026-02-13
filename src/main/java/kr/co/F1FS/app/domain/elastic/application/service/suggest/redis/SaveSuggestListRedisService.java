package kr.co.F1FS.app.domain.elastic.application.service.suggest.redis;

import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestListRedisUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveSuggestListRedisService implements SaveSuggestListRedisUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "keyword-suggest:";

    @Override
    public boolean hasKey(String keyword) {
        return redisHandler.getStringListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<String> getSuggestList(String keyword) {
        return redisHandler.getStringListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveSuggestList(String keyword, List<String> keywords) {
        redisHandler.getStringListRedisTemplate().opsForList().rightPushAll(KEY + keyword, keywords);
        redisHandler.getStringListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
