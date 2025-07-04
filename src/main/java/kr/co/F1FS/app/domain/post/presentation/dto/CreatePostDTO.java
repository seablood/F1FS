package kr.co.F1FS.app.domain.post.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    @NotBlank(message = "title은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;

    public static Post toEntity(CreatePostDTO dto, User author){
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .build();
    }
}
