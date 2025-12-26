package kr.co.F1FS.app.domain.notification.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long redisId;
    private String title;
    private String simpleContent;
    @Lob
    private String content;
    private String topic;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    public void modify(ModifyNotificationDTO dto){
        this.title = dto.getTitle();
        this.simpleContent = dto.getSimpleContent();
        this.content = dto.getContent();
    }

    @Builder
    public Notification(NotificationRedis redis, String content){
        this.redisId = redis.getId();
        this.title = redis.getTitle();
        this.simpleContent = redis.getContent();
        this.content = content;
        this.topic = redis.getTopic();
        this.createdAt = redis.getCreatedAt();
    }
}
