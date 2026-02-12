package kr.co.F1FS.app.domain.auth.application.mapper;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateAdminUserCommand;
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

    public CreateAdminUserCommand toCreateAdminUserCommand(CreateAdminUserDTO dto){
        return CreateAdminUserCommand.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .build();
    }

    public VerificationCode toVerificationCode(String email, String code){
        return VerificationCode.builder()
                .email(email)
                .code(code)
                .build();
    }
}
