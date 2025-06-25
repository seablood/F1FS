package kr.co.F1FS.app.global.presentation.dto.notification;

import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public static ResponseNotificationDTO toDto(Notification notification){
        LocalDateTime notificationTime = TimeUtil.convertToKoreanTime(notification.getCreatedAt());

        return new ResponseNotificationDTO(notification.getId(), notification.getTitle(), notification.getContent(),
                notification.getTopic(), TimeUtil.formatPostTime(notificationTime));
    }
}
