package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.driver.QueryFollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFollowDriverService implements QueryFollowDriverUseCase {
    private final FollowDriverJpaPort followDriverJpaPort;
    private final FollowMapper followMapper;

    @Override
    public FollowDriver findByFollowerUserAndFolloweeDriver(User user, Driver driver) {
        return followDriverJpaPort.findByFollowerUserAndFolloweeDriver(user, driver);
    }

    @Override
    @Cacheable(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowDriverDTO> findByFollowerUserForDTO(User user) {
        return followDriverJpaPort.findByFollowerUser(user).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> followMapper.toResponseFollowDriverDTO(followeeDriver))
                .toList();
    }
}
