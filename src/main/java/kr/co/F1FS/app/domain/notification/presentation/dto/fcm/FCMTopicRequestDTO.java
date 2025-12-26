package kr.co.F1FS.app.domain.notification.presentation.dto.fcm;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCMTopicRequestDTO {
    @NotBlank(message = "구독할 토픽을 지정해주세요.")
    private String topic;
}
