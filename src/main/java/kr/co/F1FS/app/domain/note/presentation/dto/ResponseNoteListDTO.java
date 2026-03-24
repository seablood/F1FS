package kr.co.F1FS.app.domain.note.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseNoteListDTO {
    private Long id;
    private String simpleContent;
    private String createdAt;
    private boolean isRead;

    @QueryProjection
    public ResponseNoteListDTO(Long id, String simpleContent, Timestamp createdAt, boolean isRead){
        this.id = id;
        this.simpleContent = simpleContent + "님으로부터 온 쪽지";
        this.createdAt = TimeUtil.formatPostTime(TimeUtil.convertToKoreanTime(createdAt));
        this.isRead = isRead;
    }
}
