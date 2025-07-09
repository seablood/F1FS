package kr.co.F1FS.app.domain.note.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteDTO {
    @NotBlank(message = "쪽지 내용은 필수 입력 항목입니다.")
    @Size(max = 500, message = "쪽지 내용은 최대 500글자입니다.")
    private String content;
}
