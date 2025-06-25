package kr.co.F1FS.app.domain.post.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPostDTO {
    @NotBlank(message = "title은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;
}
