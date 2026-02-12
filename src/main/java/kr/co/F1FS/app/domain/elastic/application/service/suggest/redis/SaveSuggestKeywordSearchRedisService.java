package kr.co.F1FS.app.domain.elastic.application.service.suggest.redis;

import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveSuggestKeywordSearchRedisService implements SaveSuggestKeywordSearchRedisUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "search:keywords";

    @Override
    public void increaseSearchCount(String keyword) {
        redisHandler.getStringListRedisTemplate().opsForZSet().incrementScore(KEY, keyword, 1);
    }
}
