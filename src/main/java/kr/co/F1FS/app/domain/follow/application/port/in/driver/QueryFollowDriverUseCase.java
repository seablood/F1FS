package kr.co.F1FS.app.domain.follow.application.port.in.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface QueryFollowDriverUseCase {
    FollowDriver findByFollowerUserAndFolloweeDriver(User user, Driver driver);
    List<ResponseFollowDriverDTO> findByFollowerUserForDTO(User user);
}
