package kr.co.F1FS.app.domain.reply.presentation.dto.replying;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyReplyDTO {
    @NotBlank(message = "content는 필수 입력 사항입니다.")
    private String content;
}
