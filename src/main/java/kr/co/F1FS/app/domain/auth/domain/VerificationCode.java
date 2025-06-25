package kr.co.F1FS.app.domain.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "verification_code")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String code;

    private LocalDateTime expireTime;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    @Builder
    public VerificationCode(String email, String code){
        this.email = email;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusMinutes(5);
    }
}
