package kr.co.F1FS.app.global.presentation.dto.complain.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseNoteComplainDTO {
    private Long id;
    private Long noteId;
    private String description;
}
