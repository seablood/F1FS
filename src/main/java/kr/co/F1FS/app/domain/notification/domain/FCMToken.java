package kr.co.F1FS.app.domain.notification.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "fcm_token")
public class FCMToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Long userId;

    @Builder
    public FCMToken(String token, Long userId){
        this.token = token;
        this.userId = userId;
    }
}
