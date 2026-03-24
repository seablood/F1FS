package kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;

import java.util.List;

public interface FollowUserDSLRepository {
    FollowUser findByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId);
    List<FollowUser> findAllByFollowerUser(Long followerUserId);
    List<FollowUser> findAllByFolloweeUser(Long followeeUserId);
    boolean existsByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId);
}
