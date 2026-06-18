package kr.co.F1FS.app.domain.form.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponsePostRoomFormListDTO {
    private Long id;
    private String nickname;
    private String roomTitle;
    private String createdAt;
    private boolean isConfirmed;

    @QueryProjection
    public ResponsePostRoomFormListDTO(Long id, String nickname, String roomTitle, Timestamp createdAt, boolean isConfirmed){
        this.id = id;
        this.nickname = nickname;
        this.roomTitle = roomTitle;
        this.createdAt = TimeUtil.formatPostTime(TimeUtil.convertToKoreanTime(createdAt));
        this.isConfirmed = isConfirmed;
    }
}
