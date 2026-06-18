package kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponsePostRoomSuspensionListDTO {
    private Long id;
    private String suspendNickname;
    private int durationDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp suspendUntil;

    @QueryProjection
    public ResponsePostRoomSuspensionListDTO(Long id, String suspendNickname, int durationDays, Timestamp suspendUntil){
        this.id = id;
        this.suspendNickname = suspendNickname;
        this.durationDays = durationDays;
        this.suspendUntil = suspendUntil;
    }
}
