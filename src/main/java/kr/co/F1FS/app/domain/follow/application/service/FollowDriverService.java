package kr.co.F1FS.app.domain.follow.application.service;

import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.FollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowDriverPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowDriverService implements FollowDriverUseCase {
    private final FollowMapper followMapper;
    private final FollowDriverJpaPort followDriverJpaPort;
    private final CacheEvictUtil cacheEvictUtil;
    private final DriverUseCase driverUseCase;
    private final FollowDriverPort followDriverPort;

    @Transactional
    @CacheEvict(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Driver driver = followDriverPort.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingDriver(driver);

        if(isFollowed(user, driver)){
            FollowDriver followDriver = followDriverJpaPort.findByFollowerUserAndFolloweeDriver(user, driver);
            followDriverJpaPort.delete(followDriver);
            driverUseCase.decreaseFollower(driver);
            return;
        }

        FollowDriver followDriver = followMapper.toFollowDriver(user, driver);
        followDriverJpaPort.save(followDriver);
        driverUseCase.increaseFollower(driver);

        followDriverPort.saveAndFlush(driver);
    }

    @Cacheable(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowDriverDTO> getFollowingDriver(User user){
        return followDriverJpaPort.findByFollowerUser(user);
    }

    public boolean isFollowed(User user, Driver driver){
        return followDriverJpaPort.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }
}
