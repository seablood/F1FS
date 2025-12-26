package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.global.config.oauth2.provider.OAuth2UserInfo;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final UserMapper userMapper;

    public User createEntity(CreateUserCommand command){
        return userMapper.toUser(command);
    }

    public User createEntity(CreateAdminUserDTO dto){
        return userMapper.toUser(dto);
    }

    public User createEntity(OAuth2UserInfo userInfo){
        return userMapper.toUser(userInfo);
    }

    public void updateLastLoginDate(User user){
        user.updateLastLoginDate();
    }

    public void updateRole(User user, Role role) {
        user.updateRole(role);
    }

    public void updatePassword(User user, String password) {
        user.updatePassword(password);
    }

    public void updateNickname(User user, String nickname){
        user.updateNickname(nickname);
    }

    public void updateDescription(User user, String description){
        user.updateDescription(description);
    }

    public void updateRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
    }

    public void updateSuspendUntil(User user, Integer durationDays){
        user.updateSuspendUntil(durationDays);
    }

    public void updateSuspendNum(User user){
        user.updateSuspendNum();
    }

    public void increaseFollow(User followerUser, User followeeUser){
        followerUser.changeFollowingNum(1);
        followeeUser.changeFollowerNum(1);
    }

    public void decreaseFollow(User followerUser, User followeeUser){
        followerUser.changeFollowingNum(-1);
        followeeUser.changeFollowerNum(-1);
    }
}
