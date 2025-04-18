package kr.co.F1FS.app.application.follow;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.driver.DriverService;
import kr.co.F1FS.app.presentation.driver.dto.SimpleResponseDriverDTO;
import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.model.rdb.FollowDriver;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.follow.FollowDriverRepository;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowDriverService {
    private final FollowDriverRepository followDriverRepository;
    private final CacheEvictUtil cacheEvictUtil;
    private final DriverService driverService;

    @Transactional
    @CacheEvict(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Driver driver = driverService.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingDriver(driver);

        if(isFollowed(user, driver)){
            FollowDriver followDriver = followDriverRepository.findByFollowerUserAndFolloweeDriver(user, driver);
            followDriverRepository.delete(followDriver);
            driver.decreaseFollower();
            return;
        }

        FollowDriver followDriver = FollowDriver.builder()
                .followerUser(user)
                .followeeDriver(driver)
                .build();
        followDriverRepository.save(followDriver);
        driver.increaseFollower();
    }

    @Cacheable(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<SimpleResponseDriverDTO> getFollowingDriver(User user){
        return followDriverRepository.findByFollowerUser(user).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> SimpleResponseDriverDTO.toDto(followeeDriver))
                .toList();
    }

    public boolean isFollowed(User user, Driver driver){
        return followDriverRepository.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }
}
