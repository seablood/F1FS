package kr.co.F1FS.app.domain.model.rdb;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.model.redis.NotificationRedis;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;
    private String topic;
    private Long contentId;
    private LocalDateTime createdAt;

    @Builder
    public Notification(NotificationRedis redis){
        this.title = redis.getTitle();
        this.content = redis.getContent();
        this.topic = redis.getTopic();
        this.contentId = redis.getContentId();
        this.createdAt = redis.getCreatedAt();
    }
}
