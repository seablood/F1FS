package kr.co.F1FS.app.domain.auth.application.service.blackList;

import kr.co.F1FS.app.domain.auth.application.port.in.blackList.CheckBlackListUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckBlackListService implements CheckBlackListUseCase {
    private final RedisHandler redisHandler;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisHandler.getRedisTemplate().hasKey(BLACKLIST_PREFIX+token));
    }
}
