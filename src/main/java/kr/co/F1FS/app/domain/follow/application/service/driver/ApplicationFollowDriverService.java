package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.UpdateDriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.port.in.driver.*;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFollowDriverService implements FollowDriverUseCase {
    private final CreateFollowDriverUseCase createFollowDriverUseCase;
    private final DeleteFollowDriverUseCase deleteFollowDriverUseCase;
    private final QueryFollowDriverUseCase queryFollowDriverUseCase;
    private final CheckFollowDriverUseCase checkFollowDriverUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final QueryDriverUseCase queryDriverUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    @CacheEvict(value = "FollowingDriver", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Driver driver = queryDriverUseCase.findById(id);
        cacheEvictUtil.evictCachingDriver(driver);

        if(checkFollowDriverUseCase.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver)){
            FollowDriver followDriver = queryFollowDriverUseCase.findByFollowerUserAndFolloweeDriver(user, driver);
            deleteFollowDriverUseCase.delete(followDriver);
            updateDriverUseCase.decreaseFollower(driver);
            return;
        }

        createFollowDriverUseCase.save(user, driver);
        updateDriverUseCase.increaseFollower(driver);
    }

    @Override
    public List<ResponseFollowDriverDTO> getFollowingDriver(User user){
        return queryFollowDriverUseCase.findByFollowerUserForDTO(user);
    }

    @Override
    public List<ResponseFollowDriverDTO> getFollowingDriverByNickname(String nickname) {
        User user = queryUserUseCase.findByNickname(nickname);
        return queryFollowDriverUseCase.findByFollowerUserForDTO(user);
    }
}
