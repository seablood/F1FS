package kr.co.F1FS.app.domain.elastic.application.service.user.redis;

import kr.co.F1FS.app.domain.elastic.application.port.in.user.redis.SaveUserSuggestListRedisUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDocumentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveUserSuggestListRedisService implements SaveUserSuggestListRedisUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "user-suggest:";

    @Override
    public boolean hasKey(String keyword) {
        return redisHandler.getUserSuggestListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<ResponseUserDocumentDTO> getSuggestList(String keyword) {
        return redisHandler.getUserSuggestListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveSuggestList(String keyword, List<ResponseUserDocumentDTO> list) {
        redisHandler.getUserSuggestListRedisTemplate().opsForList().rightPushAll(KEY + keyword, list);
        redisHandler.getUserSuggestListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
