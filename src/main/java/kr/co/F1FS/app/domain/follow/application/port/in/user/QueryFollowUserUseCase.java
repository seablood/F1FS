package kr.co.F1FS.app.domain.follow.application.port.in.user;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;

import java.util.List;

public interface QueryFollowUserUseCase {
    FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    List<ResponseFollowUserDTO> findByFollowerUserForDTO(User user);
    List<ResponseFollowUserDTO> findByFolloweeUserForDTO(User user);
    List<ResponseUserIdDTO> findByFolloweeUserIdForDTO(User user);
}
