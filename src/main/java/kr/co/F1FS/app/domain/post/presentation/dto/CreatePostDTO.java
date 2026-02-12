package kr.co.F1FS.app.domain.post.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    @NotBlank(message = "title은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;
    private List<String> tags;
}
