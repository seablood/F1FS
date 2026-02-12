package kr.co.F1FS.app.global.presentation.dto.notification;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import kr.co.F1FS.app.global.util.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMPushDTO {
    @NotBlank(message = "title은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "Simple Content는 필수 입력 항목입니다.")
    private String simpleContent;
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;
    private String author;
    @Enumerated(value = EnumType.STRING)
    private Topic topics;
}
