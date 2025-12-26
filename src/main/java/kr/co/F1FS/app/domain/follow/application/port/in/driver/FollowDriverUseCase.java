package kr.co.F1FS.app.domain.follow.application.port.in.driver;

import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowDriverUseCase {
    void toggle(User user, Long id);
    List<ResponseFollowDriverDTO> getFollowingDriver(User user);
    List<ResponseFollowDriverDTO> getFollowingDriverByNickname(String nickname);
}
