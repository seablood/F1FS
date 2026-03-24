package kr.co.F1FS.app.domain.suggest.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "suggest")
@SQLDelete(sql = "UPDATE suggest SET deleted = true WHERE id = ?")
public class Suggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;
    private String title;
    @Column(length = 500)
    private String content;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    private boolean isConfirmed;
    private boolean deleted = false;

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
