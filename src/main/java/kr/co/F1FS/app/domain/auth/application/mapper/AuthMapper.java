package kr.co.F1FS.app.domain.auth.application.mapper;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
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

    public VerificationCode toVerificationCode(User user, String code){
        return VerificationCode.builder()
                .email(user.getEmail())
                .code(code)
                .build();
    }

    public VerificationCode toVerificationCode(ResponseUserDTO dto, String code){
        return VerificationCode.builder()
                .email(dto.getEmail())
                .code(code)
                .build();
    }
}
