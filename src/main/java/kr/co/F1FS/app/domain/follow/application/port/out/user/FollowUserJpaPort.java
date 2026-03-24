package kr.co.F1FS.app.domain.follow.application.port.out.user;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;

import java.util.List;

public interface FollowUserJpaPort {
    FollowUser save(FollowUser followUser);
    FollowUser findByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId);
    List<FollowUser> findAllByFollowerUser(Long followerUserId);
    List<FollowUser> findAllByFolloweeUser(Long followeeUserId);
    boolean existsByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId);
    void delete(FollowUser followUser);
}
