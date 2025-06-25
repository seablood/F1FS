package kr.co.F1FS.app.domain.suspend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

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
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp suspendUntil;
    private String description;
    @Column(length = 500)
    private String paraphrase;

    @Builder
    public SuspensionLog(User suspendUser, int durationDays, Timestamp suspendUntil,
                         String description, String paraphrase){
        this.suspendUser = suspendUser;
        this.durationDays = durationDays;
        this.suspendUntil = suspendUntil;
        this.description = description;
        this.paraphrase = paraphrase;
    }
}
