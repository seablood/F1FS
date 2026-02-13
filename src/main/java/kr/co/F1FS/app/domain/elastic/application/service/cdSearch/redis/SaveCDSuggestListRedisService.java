package kr.co.F1FS.app.domain.elastic.application.service.cdSearch.redis;

import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.redis.SaveCDSuggestListRedisUseCase;
import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveCDSuggestListRedisService implements SaveCDSuggestListRedisUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "cd-suggest:";

    @Override
    public boolean hasKey(String keyword) {
        return redisHandler.getCdSuggestListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<CDSearchSuggestionDTO> getSuggestList(String keyword) {
        return redisHandler.getCdSuggestListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveSuggestList(String keyword, List<CDSearchSuggestionDTO> combine) {
        redisHandler.getCdSuggestListRedisTemplate().opsForList().rightPushAll(KEY + keyword, combine);
        redisHandler.getCdSuggestListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
