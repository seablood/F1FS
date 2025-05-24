package kr.co.F1FS.app.presentation.admin.user.dto;

import kr.co.F1FS.app.domain.model.rdb.UserComplain;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserComplainDTO {
    private String toUserNickname;
    private String fromUserNickname;
    private String description;

    public static ResponseUserComplainDTO toDto(UserComplain complain){
        return new ResponseUserComplainDTO(complain.getToUser().getNickname(), complain.getFromUser().getNickname(),
                complain.getDescription());
    }
}
