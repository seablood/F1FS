package kr.co.F1FS.app.domain.session.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @ManyToOne
    @JoinColumn(name = "grand_prix_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GrandPrix grandPrix;

    @Builder
    public Session(SessionType sessionType, GrandPrix grandPrix){
        this.sessionType = sessionType;
        this.grandPrix = grandPrix;
    }
}
