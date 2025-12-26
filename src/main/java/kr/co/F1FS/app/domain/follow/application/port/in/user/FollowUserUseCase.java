package kr.co.F1FS.app.domain.follow.application.port.in.user;

import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;

import java.util.List;

public interface FollowUserUseCase {
    void toggle(User followerUser, String followeeNickname);
    List<ResponseFollowUserDTO> findFollowers(String nickname);
    List<ResponseFollowUserDTO> findFollowees(String nickname);
    List<ResponseFollowUserDTO> findFollowersAuth(User user);
    List<ResponseFollowUserDTO> findFolloweesAuth(User user);
}
