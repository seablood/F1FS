package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.SimpleResponseDriverDTO;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.FollowDriver;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.FollowDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowDriverService {
    private final FollowDriverRepository followDriverRepository;
    private final UserService userService;
    private final DriverService driverService;

    @Transactional
    public void toggle(User user, Long id){
        Driver driver = driverService.findByIdNotDTO(id);

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

    public List<SimpleResponseDriverDTO> getFollowingDriver(String nickname){
        User user = userService.findByNicknameNotDTO(nickname);
        return followDriverRepository.findByFollowerUser(user).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> SimpleResponseDriverDTO.toDto(followeeDriver))
                .toList();
    }

    public List<SimpleResponseDriverDTO> getFollowingDriverAuth(User user){
        return followDriverRepository.findByFollowerUser(user).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> SimpleResponseDriverDTO.toDto(followeeDriver))
                .toList();
    }

    public boolean isFollowed(User user, Driver driver){
        return followDriverRepository.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }
}
