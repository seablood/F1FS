package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.follow.application.port.in.constructor.SaveFollowConstructorListUseCase;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveFollowConstructorListService implements SaveFollowConstructorListUseCase {
    private final RedisHandler redisHandler;
    private static final String KEY = "follow-constructor-list::";

    @Override
    public boolean hasKey(Long keyword) {
        return redisHandler.getFollowConstructorListRedisTemplate().hasKey(KEY + keyword);
    }

    @Override
    public List<ResponseFollowConstructorDTO> getFollowingList(Long keyword) {
        return redisHandler.getFollowConstructorListRedisTemplate().opsForList().range(KEY + keyword, 0, -1);
    }

    @Override
    public void saveFollowingList(Long keyword, List<ResponseFollowConstructorDTO> list) {
        redisHandler.getFollowConstructorListRedisTemplate().opsForList().rightPushAll(KEY + keyword, list);
        redisHandler.getFollowConstructorListRedisTemplate().expire(KEY + keyword, Duration.ofMinutes(5));
    }
}
