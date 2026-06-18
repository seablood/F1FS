package kr.co.F1FS.app.domain.form.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponsePostRoomDeleteFormListDTO {
    private Long id;
    private String userNickname;
    private String title;
    private String description;
    private boolean isConfirmed;
    private String createdAt;

    @QueryProjection
    public ResponsePostRoomDeleteFormListDTO(Long id, String userNickname, String title, String description,
                                             boolean isConfirmed, Timestamp createdAt){
        this.id = id;
        this.userNickname = userNickname;
        this.title = title;
        this.description = description;
        this.isConfirmed = isConfirmed;
        this.createdAt = TimeUtil.formatPostTime(TimeUtil.convertToKoreanTime(createdAt));
    }
}
