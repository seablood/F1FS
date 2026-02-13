package kr.co.F1FS.app.domain.elastic.application.service.grandPrix.redis;

import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.redis.SaveGrandPrixSuggestListRedisUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseSuggestGrandPrixSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveGrandPrixSuggestListRedisService implements SaveGrandPrixSuggestListRedisUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "grand-prix-suggest:";

    @Override
    public boolean hasKey(String keyword) {
        return redisHandler.getGrandPrixSuggestListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<ResponseSuggestGrandPrixSearchDTO> getSuggestList(String keyword) {
        return redisHandler.getGrandPrixSuggestListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveSuggestList(String keyword, List<ResponseSuggestGrandPrixSearchDTO> list) {
        redisHandler.getGrandPrixSuggestListRedisTemplate().opsForList().rightPushAll(KEY + keyword, list);
        redisHandler.getGrandPrixSuggestListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
