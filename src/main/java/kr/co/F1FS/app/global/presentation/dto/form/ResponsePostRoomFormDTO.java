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
public class ResponsePostRoomFormDTO {
    private Long id;
    private String nickname;
    private String roomTitle;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    private boolean isPublic;
    private String password;
}
