package kr.co.F1FS.app.domain.follow.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

public interface FollowUserPort {
    User findByNicknameNotDTO(String nickname);
    void increaseFollow(User followerUser, User followeeUser);
    void decreaseFollow(User followerUser, User followeeUser);
}
