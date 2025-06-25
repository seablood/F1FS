package kr.co.F1FS.app.domain.auth.application.service;

import kr.co.F1FS.app.domain.auth.application.port.in.BlackListUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BlackListService implements BlackListUseCase {
    private final RedisHandler redisHandler;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    public void addBlackList(String token, long expirationMillis){ // 블랙리스트 추가
        redisHandler.executeOperations(() -> redisHandler.getValueOperations().
                set(BLACKLIST_PREFIX + token, "invalid", Duration.ofMillis(expirationMillis)));
    }

    public boolean isBlacklisted(String token) { // 블랙리스트 포함 여부
        return Boolean.TRUE.equals(redisHandler.getRedisTemplate().hasKey(BLACKLIST_PREFIX+token));
    }
}
