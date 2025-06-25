package kr.co.F1FS.app.domain.admin.auth.presentation.dto;

import kr.co.F1FS.app.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {
    private String username;
    private String nickname;
    private String email;
    private int follower;
    private int following;

    public static ResponseUserDTO toDto(User user){
        return new ResponseUserDTO(user.getUsername(), user.getNickname(), user.getEmail(), user.getFollowerNum(),
                user.getFollowingNum());
    }
}
