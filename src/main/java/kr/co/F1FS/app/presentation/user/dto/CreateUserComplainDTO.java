package kr.co.F1FS.app.presentation.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserComplainDTO {
    @NotBlank(message = "신고할 유저의 닉네임은 필수 항목입니다.")
    private String toUserNickname;
    @NotBlank(message = "신고 사유는 필수 항목입니다.")
    private String description;
    private String paraphrase;
}
