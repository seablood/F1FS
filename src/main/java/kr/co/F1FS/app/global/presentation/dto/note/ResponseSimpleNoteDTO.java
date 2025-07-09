package kr.co.F1FS.app.global.presentation.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSimpleNoteDTO {
    private Long id;
    private String simpleContent;
    private String createdAt;
    private boolean isRead;
}
