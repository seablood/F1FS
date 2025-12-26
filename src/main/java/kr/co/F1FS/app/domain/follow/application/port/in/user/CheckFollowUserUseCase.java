package kr.co.F1FS.app.domain.follow.application.port.in.user;

import kr.co.F1FS.app.domain.user.domain.User;

public interface CheckFollowUserUseCase {
    boolean existsFollowUserByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
}
