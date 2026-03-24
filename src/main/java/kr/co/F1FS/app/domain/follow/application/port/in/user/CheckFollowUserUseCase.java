package kr.co.F1FS.app.domain.follow.application.port.in.user;

public interface CheckFollowUserUseCase {
    boolean existsByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId);
}
