package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.port.in.driver.CreateFollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowDriverService implements CreateFollowDriverUseCase {
    private final FollowDriverJpaPort followDriverJpaPort;
    private final FollowDriverDomainService followDriverDomainService;

    @Override
    public void save(User user, Driver driver) {
        FollowDriver followDriver = followDriverDomainService.createEntity(user, driver);

        followDriverJpaPort.save(followDriver);
    }
}
