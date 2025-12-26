package kr.co.F1FS.app.domain.user.application.mapper;

import kr.co.F1FS.app.domain.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.global.config.oauth2.provider.OAuth2UserInfo;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(CreateUserCommand command){
        return User.builder()
                .username(command.getUsername())
                .password(command.getPassword())
                .email(command.getEmail())
                .nickname(command.getNickname())
                .description("자신을 소개해주세요!")
                .build();
    }

    public User toUser(CreateAdminUserDTO dto){
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .build();
    }

    public User toUser(OAuth2UserInfo userInfo){
        return User.builder()
                .username(userInfo.getProvider()+userInfo.getProviderId())
                .nickname(userInfo.getProvider()+userInfo.getProviderId())
                .email(userInfo.getEmail())
                .provider(userInfo.getProvider())
                .providerId(userInfo.getProviderId())
                .build();
    }

    public ResponseUserDTO toResponseUserDTO(User user){
        return ResponseUserDTO.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .follower(user.getFollowerNum())
                .following(user.getFollowingNum())
                .build();
    }
}
