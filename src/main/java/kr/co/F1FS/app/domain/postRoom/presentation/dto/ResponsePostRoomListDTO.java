package kr.co.F1FS.app.domain.postRoom.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponsePostRoomListDTO {
    private Long id;
    private String masterUserNickname;
    private String roomTitle;
    private String description;
    private boolean isPublic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp updatedAt;

    @QueryProjection
    public ResponsePostRoomListDTO(Long id, String masterUserNickname, String roomTitle, String description,
                                   boolean isPublic, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.masterUserNickname = masterUserNickname;
        this.roomTitle = roomTitle;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
