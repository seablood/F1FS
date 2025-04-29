package kr.co.F1FS.app.domain.model.rdb;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "fcm_notification")
public class FCMNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Long userId;

    @Builder
    public FCMNotification(String token, Long userId){
        this.token = token;
        this.userId = userId;
    }
}
