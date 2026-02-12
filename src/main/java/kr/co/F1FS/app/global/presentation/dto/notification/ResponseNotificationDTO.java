package kr.co.F1FS.app.global.presentation.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNotificationDTO {
    private Long id;
    private String title;
    private String content;
    private String topic;
    private String createdAt;
}
