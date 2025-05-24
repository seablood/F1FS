package kr.co.F1FS.app.domain.model.rdb;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "suspension_log")
public class SuspensionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "suspend_user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User suspendUser;
    private int durationDays;
    private LocalDateTime suspendUntil;
    private String description;
    private String paraphrase;

    @Builder
    public SuspensionLog(User suspendUser, int durationDays, LocalDateTime suspendUntil,
                         String description, String paraphrase){
        this.suspendUser = suspendUser;
        this.durationDays = durationDays;
        this.suspendUntil = suspendUntil;
        this.description = description;
        this.paraphrase = paraphrase;
    }
}
