package kr.co.F1FS.app.domain.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDescriptionDTO {
    @NotBlank(message = "변경할 사용자 소개는 필수 입력 항목입니다.")
    @Size(max = 500, message = "사용자 소개는 500자 이하입니다.")
    private String description;
}
