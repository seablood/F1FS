package kr.co.F1FS.app.domain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.follow.application.port.out.FollowUserPort;
import kr.co.F1FS.app.domain.user.application.service.UserService;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowUserAdapter implements FollowUserPort {
    private final UserService userService;

    @Override
    public User findByNicknameNotDTO(String nickname) {
        return userService.findByNicknameNotDTO(nickname);
    }

    @Override
    public void increaseFollow(User followerUser, User followeeUser) {
        followerUser.changeFollowingNum(1);
        followeeUser.changeFollowerNum(1);
    }

    @Override
    public void decreaseFollow(User followerUser, User followeeUser) {
        followerUser.changeFollowingNum(-1);
        followeeUser.changeFollowerNum(-1);
    }
}
