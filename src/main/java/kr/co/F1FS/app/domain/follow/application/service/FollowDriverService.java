package kr.co.F1FS.app.domain.follow.application.service;

import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.FollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowDriverPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowDriverRepository;
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
    private final FollowDriverRepository followDriverRepository;
    private final CacheEvictUtil cacheEvictUtil;
    private final DriverUseCase driverUseCase;
    private final FollowDriverPort followDriverPort;

    @Transactional
    @CacheEvict(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Driver driver = followDriverPort.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingDriver(driver);

        if(isFollowed(user, driver)){
            FollowDriver followDriver = followDriverRepository.findByFollowerUserAndFolloweeDriver(user, driver);
            followDriverRepository.delete(followDriver);
            driverUseCase.decreaseFollower(driver);
            return;
        }

        FollowDriver followDriver = followMapper.toFollowDriver(user, driver);
        followDriverRepository.save(followDriver);
        driverUseCase.increaseFollower(driver);

        followDriverPort.saveAndFlush(driver);
    }

    @Cacheable(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowDriverDTO> getFollowingDriver(User user){
        return followDriverRepository.findByFollowerUser(user).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> followMapper.toResponseFollowDriverDTO(followeeDriver))
                .toList();
    }

    public boolean isFollowed(User user, Driver driver){
        return followDriverRepository.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }
}
