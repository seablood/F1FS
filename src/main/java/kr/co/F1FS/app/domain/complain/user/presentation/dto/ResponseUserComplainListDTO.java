package kr.co.F1FS.app.domain.complain.user.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseUserComplainListDTO {
    private Long id;
    private String toUserNickname;
    private String description;

    @QueryProjection
    public ResponseUserComplainListDTO(Long id, String toUserNickname, String description){
        this.id = id;
        this.toUserNickname = toUserNickname;
        this.description = description;
    }
}
