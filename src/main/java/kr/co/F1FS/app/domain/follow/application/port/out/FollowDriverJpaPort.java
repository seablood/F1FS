package kr.co.F1FS.app.domain.follow.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowDriverJpaPort {
    FollowDriver save(FollowDriver followDriver);
    FollowDriver findByFollowerUserAndFolloweeDriver(User user, Driver driver);
    List<ResponseFollowDriverDTO> findByFollowerUser(User user);
    boolean existsFollowDriverByFollowerUserAndFolloweeDriver(User user, Driver driver);
    void delete(FollowDriver followDriver);
}
