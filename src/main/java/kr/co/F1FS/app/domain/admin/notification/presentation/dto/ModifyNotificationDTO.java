package kr.co.F1FS.app.domain.admin.notification.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNotificationDTO {
    @NotBlank(message = "title은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "Simple Content는 필수 입력 항목입니다.")
    private String simpleContent;
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;
}
