package kr.co.F1FS.app.domain.model.rdb;

import jakarta.persistence.*;
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
    private String content;

    private LocalDateTime createdAt;

    @Builder
    public Notification(String title, String content){
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
