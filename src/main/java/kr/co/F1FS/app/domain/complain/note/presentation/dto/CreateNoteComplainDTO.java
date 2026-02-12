package kr.co.F1FS.app.domain.complain.note.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteComplainDTO {
    @NotBlank(message = "신고 사유는 필수 항목입니다.")
    private String description;
    private String paraphrase;
}
