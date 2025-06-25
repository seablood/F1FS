package kr.co.F1FS.app.domain.user.application.mapper;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
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
