package kr.co.F1FS.app.domain.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNicknameDTO {
    @NotBlank(message = "변경할 닉네임은 필수 입력 항목입니다.")
    @Size(max = 20, message = "닉네임은 20자 이하입니다.")
    private String newNickname;
}
