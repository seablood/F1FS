package kr.co.F1FS.app.domain.follow.application.port.out;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;

import java.util.List;

public interface FollowUserJpaPort {
    FollowUser save(FollowUser followUser);
    FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    List<ResponseFollowUserDTO> findByFolloweeUser(User user);
    List<ResponseFollowUserDTO> findByFollowerUser(User user);
    List<ResponseUserIdDTO> findByFolloweeUserId(User user);
    boolean existsFollowUserByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    void delete(FollowUser followUser);
}
