package kr.co.F1FS.app.domain.follow.application.port.out.user;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowUserJpaPort {
    FollowUser save(FollowUser followUser);
    FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    List<FollowUser> findByFolloweeUser(User user);
    List<FollowUser> findByFollowerUser(User user);
    boolean existsFollowUserByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    void delete(FollowUser followUser);
}
