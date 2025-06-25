package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.application.service.DriverService;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowDriverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowDriverAdapter implements FollowDriverPort {
    private final DriverService driverService;

    @Override
    public Driver findByIdNotDTO(Long id) {
        return driverService.findByIdNotDTO(id);
    }

    @Override
    public void increaseFollower(Driver driver) {
        driver.increaseFollower();
    }

    @Override
    public void decreaseFollower(Driver driver) {
        driver.decreaseFollower();
    }
}
