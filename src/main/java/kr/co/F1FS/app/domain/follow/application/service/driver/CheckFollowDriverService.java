package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.port.in.driver.CheckFollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckFollowDriverService implements CheckFollowDriverUseCase {
    private final FollowDriverJpaPort followDriverJpaPort;

    @Override
    public boolean existsFollowDriverByFollowerUserAndFolloweeDriver(User user, Driver driver) {
        return followDriverJpaPort.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }
}
