package kr.co.F1FS.app.domain.follow.application.port.in.user;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;

public interface DeleteFollowUserUseCase {
    void delete(FollowUser followUser);
}
