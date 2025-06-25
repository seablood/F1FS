package kr.co.F1FS.app.domain.auth.application.mapper;

import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public CreateUserCommand toCreateUserCommand(CreateUserDTO dto){
        return CreateUserCommand.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .build();
    }
}
