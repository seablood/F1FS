package kr.co.F1FS.app.domain.email.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.F1FS.app.global.config.email.EmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {
    @Email(message = "올바른 email 형식을 입력해주세요")
    @NotBlank(message = "email은 필수 입력 항목입니다.")
    private String to;
    @NotBlank(message = "subject는 필수 입력 항목입니다.")
    private String subject;
    @NotNull(message = "content는 필수 입력 항목입니다.")
    private String content;
    @NotNull(message = "emailType은 필수 입력 항목입니다.")
    private EmailType emailType;
}
