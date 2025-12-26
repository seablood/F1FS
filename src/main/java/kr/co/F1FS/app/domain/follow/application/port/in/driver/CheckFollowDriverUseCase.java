package kr.co.F1FS.app.domain.follow.application.port.in.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CheckFollowDriverUseCase {
    boolean existsFollowDriverByFollowerUserAndFolloweeDriver(User user, Driver driver);
}
