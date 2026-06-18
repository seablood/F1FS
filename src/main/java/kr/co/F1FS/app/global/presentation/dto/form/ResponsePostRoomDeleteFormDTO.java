package kr.co.F1FS.app.global.presentation.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostRoomDeleteFormDTO {
    private Long id;
    private String userNickname;
    private String postRoomTitle;
    private Long postRomId;
    private String title;
    private String description;
    private boolean isConfirmed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
}
