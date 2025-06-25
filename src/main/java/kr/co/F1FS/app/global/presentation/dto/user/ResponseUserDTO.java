package kr.co.F1FS.app.global.presentation.dto.user;

import kr.co.F1FS.app.domain.user.domain.User;
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

    public static ResponseUserDTO toDto(User user){
        return new ResponseUserDTO(user.getUsername(), user.getNickname(), user.getEmail(), user.getFollowerNum(),
                user.getFollowingNum());
    }
}
