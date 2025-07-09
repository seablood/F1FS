package kr.co.F1FS.app.domain.note.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 500)
    private String content;
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User fromUser;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updatedAt;
    private boolean isRead;

    public void modify(ModifyNoteDTO dto){
        this.content = dto.getContent();
    }

    public void updateIsRead(){
        this.isRead = true;
    }

    @Builder
    public Note(String content, User toUser, User fromUser){
        this.content = content;
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.isRead = false;
    }
}
