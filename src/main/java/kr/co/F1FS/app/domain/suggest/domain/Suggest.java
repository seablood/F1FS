package kr.co.F1FS.app.domain.suggest.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "suggest")
public class Suggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User fromUser;
    private String title;
    @Column(length = 500)
    private String content;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    private boolean isConfirmed;

    public void modify(ModifySuggestDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void updateConfirmed(boolean isConfirmed){
        this.isConfirmed = isConfirmed;
    }

    @Builder
    public Suggest(User fromUser, String title, String content){
        this.fromUser = fromUser;
        this.title = title;
        this.content = content;
        this.isConfirmed = false;
    }
}
