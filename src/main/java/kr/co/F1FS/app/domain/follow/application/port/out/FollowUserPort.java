package kr.co.F1FS.app.domain.follow.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

public interface FollowUserPort {
    User findByNicknameNotDTO(String nickname);
    void saveAndFlush(User user);
}
