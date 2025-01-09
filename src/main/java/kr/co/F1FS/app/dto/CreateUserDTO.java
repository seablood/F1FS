package kr.co.F1FS.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.co.F1FS.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "username은 필수 입력 항목입니다.")
    private String username;
    @NotBlank(message = "password는 필수 입력 항목입니다.")
    private String password;
    @NotBlank(message = "nickname은 필수 입력 항목입니다.")
    private String nickname;
    @Email(message = "올바른 email 형식을 입력해주세요")
    @NotBlank(message = "email은 필수 입력 항목입니다.")
    private String email;

    public static User toEntity(CreateUserDTO userDTO){
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .email(userDTO.getEmail())
                .build();
    }
}
