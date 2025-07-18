package kr.co.F1FS.app.domain.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {
    private String username;
    private String password;
    private String nickname;
    private String email;
}
