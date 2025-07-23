package kr.co.F1FS.app.domain.session.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private SessionType sessionType;
    private String time;

    @Builder
    public Session(SessionType sessionType, String time){
        this.sessionType = sessionType;
        this.time = time;
    }
}
