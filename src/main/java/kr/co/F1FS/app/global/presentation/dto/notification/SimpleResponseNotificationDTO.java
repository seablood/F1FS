package kr.co.F1FS.app.global.presentation.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseNotificationDTO {
    private Long id;
    private Long redisId;
    private String title;
    private String simpleContent;
    private String topic;
    private String createdAt;
}
