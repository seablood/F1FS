package kr.co.F1FS.app.global.presentation.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNotificationRedisDTO {
    private Long id;
    private String title;
    private String content;
    private Long contentId;
    private String type;
    private boolean isRead;
    private String createdAt;
}
