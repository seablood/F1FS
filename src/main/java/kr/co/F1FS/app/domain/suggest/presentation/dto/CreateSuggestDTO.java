package kr.co.F1FS.app.domain.suggest.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSuggestDTO {
    @NotBlank(message = "건의 사항에서 제목은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "건의 사항에서 내용은 반드시 작성해야 합니다.")
    private String content;

    public static Suggest toEntity(User user, CreateSuggestDTO dto){
        return Suggest.builder()
                .fromUser(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
