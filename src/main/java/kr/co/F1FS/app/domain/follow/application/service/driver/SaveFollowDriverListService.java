package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.follow.application.port.in.driver.SaveFollowDriverListUseCase;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveFollowDriverListService implements SaveFollowDriverListUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "follow-driver-list::";

    @Override
    public boolean hasKey(Long keyword) {
        return redisHandler.getFollowDriverListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<ResponseFollowDriverDTO> getFollowingList(Long keyword) {
        return redisHandler.getFollowDriverListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveFollowingList(Long keyword, List<ResponseFollowDriverDTO> list) {
        redisHandler.getFollowDriverListRedisTemplate().opsForList().rightPushAll(KEY + keyword, list);
        redisHandler.getFollowDriverListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
