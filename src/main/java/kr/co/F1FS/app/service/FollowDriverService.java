package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.FollowDriver;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.FollowDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowDriverService {
    private final FollowDriverRepository followDriverRepository;
    private final UserService userService;
    private final DriverService driverService;

    @Transactional
    public void toggle(String username, String name){
        User user = userService.findByUsernameNotDTO(username);
        Driver driver = driverService.findByName(name);

        if(isFollowed(user, driver)){
            FollowDriver followDriver = followDriverRepository.findByFollowerUserAndFolloweeDriver(user, driver);
            followDriverRepository.delete(followDriver);
            driver.decreaseFollower();
        }

        else {
            FollowDriver followDriver = FollowDriver.builder()
                    .followerUser(user)
                    .followeeDriver(driver)
                    .build();
            followDriverRepository.save(followDriver);
            driver.increaseFollower();
        }
    }

    public boolean isFollowed(User user, Driver driver){
        return followDriverRepository.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }
}
