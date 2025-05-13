package kr.co.F1FS.app.domain.model.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRedis {
    private Long id;
    private String title;
    private String content;
    private String topic;
    private Long contentId;
    private LocalDateTime createdAt;

    @Builder
    public NotificationRedis(Long id, String title, String content, String topic){
        this.id = id;
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.createdAt = LocalDateTime.now();
    }
}
