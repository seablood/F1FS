package kr.co.F1FS.app.domain.follow.application.port.in.user;

import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateFollowUserUseCase {
    void save(User followerUser, User followeeUser);
}
