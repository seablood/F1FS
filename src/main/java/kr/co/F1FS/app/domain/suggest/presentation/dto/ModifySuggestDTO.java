package kr.co.F1FS.app.domain.suggest.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifySuggestDTO {
    @NotBlank(message = "변경할 제목은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "변경할 건의 사항은 필수 입력 항목입니다.")
    private String content;
}
