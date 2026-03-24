package kr.co.F1FS.app.domain.follow.application.port.in.user;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;

import java.util.List;

public interface QueryFollowUserUseCase {
    FollowUser findByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId);
    List<ResponseFollowUserDTO> findAllByFollowerUserForDTO(Long followerUserId);
    List<ResponseFollowUserDTO> findAllByFolloweeUserForDTO(Long followeeUserId);
    List<ResponseUserIdDTO> findByFolloweeUserIdForDTO(Long userId);
}
