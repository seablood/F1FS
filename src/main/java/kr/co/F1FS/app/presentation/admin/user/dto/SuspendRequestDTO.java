package kr.co.F1FS.app.presentation.admin.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuspendRequestDTO {
    @NotBlank(message = "징계를 적용할 유저가 존재해야 합니다.")
    private String nickname;
    @NotBlank(message = "징계는 사유가 존재해야 합니다.")
    private String description;
    private String paraphrase;
    @Min(value = 7, message = "징계는 최소 일주일입니다.")
    private Integer durationDays;
}
