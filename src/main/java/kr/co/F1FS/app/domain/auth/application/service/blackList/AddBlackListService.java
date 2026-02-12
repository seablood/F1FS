package kr.co.F1FS.app.domain.auth.application.service.blackList;

import kr.co.F1FS.app.domain.auth.application.port.in.blackList.AddBlackListUseCase;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AddBlackListService implements AddBlackListUseCase {
    private final RedisHandler redisHandler;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Override
    public void addBlackList(String token, long expirationMillis) {
        redisHandler.executeOperations(() -> redisHandler.getValueOperations().
                set(BLACKLIST_PREFIX + token, "invalid", Duration.ofMillis(expirationMillis)));
    }
}
