package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserDTO {
    private String username;
    private String nickname;
    private String email;

    public static ResponseUserDTO toDto(User user){
        return new ResponseUserDTO(user.getUsername(), user.getNickname(), user.getEmail());
    }
}