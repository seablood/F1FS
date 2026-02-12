package kr.co.F1FS.app.global.presentation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseUserDTO {
    private String username;
    private String nickname;
    private String email;
    private int follower;
    private int following;
}
