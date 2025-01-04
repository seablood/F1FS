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
    private String username;
    private String password;
    private String nickname;
    @Email
    @NotBlank
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
