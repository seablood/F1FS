package kr.co.F1FS.app.service;

import kr.co.F1FS.app.config.redis.RedisConfig;
import kr.co.F1FS.app.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BlackListService {
    private final RedisConfig redisConfig;
    private final RedisHandler redisHandler;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    public void addBlackList(String token, long expirationMillis){ // 블랙리스트 추가
        redisHandler.executeOperations(() -> redisHandler.getValueOperations().
                set(BLACKLIST_PREFIX + token, "invalid", Duration.ofMillis(expirationMillis)));
    }

    public boolean isBlacklisted(String token) { // 블랙리스트 포함 여부
        return Boolean.TRUE.equals(redisConfig.redisTemplate().hasKey(BLACKLIST_PREFIX+token));
    }
}
