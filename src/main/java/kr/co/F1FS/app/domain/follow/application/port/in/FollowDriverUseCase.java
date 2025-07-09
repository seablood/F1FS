package kr.co.F1FS.app.domain.follow.application.port.in;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowDriverUseCase {
    void toggle(User user, Long id);
    List<ResponseFollowDriverDTO> getFollowingDriver(User user);
    boolean isFollowed(User user, Driver driver);
}
