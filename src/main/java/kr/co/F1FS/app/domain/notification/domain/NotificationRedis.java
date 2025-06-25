package kr.co.F1FS.app.domain.notification.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRedis {
    private Long id;
    private String title;
    private String content;
    private String topic;
    private Long contentId;
    private NotificationType type;
    private boolean isRead;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    public void updateIsRead(boolean isRead){
        this.isRead = isRead;
    }

    @Builder
    public NotificationRedis(Long id, String title, String content, String topic, NotificationType type){
        this.id = id;
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.type = type;
        this.isRead = false;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
