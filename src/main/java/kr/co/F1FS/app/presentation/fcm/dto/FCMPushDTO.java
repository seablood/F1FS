package kr.co.F1FS.app.presentation.fcm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMPushDTO {
    @NotBlank(message = "topic은 필수 입력 항목입니다.")
    private String topic;
    @NotBlank(message = "title은 필수 입력 항목입니다.")
    private String title;
    @NotBlank(message = "content는 필수 입력 항목입니다.")
    private String content;
}
