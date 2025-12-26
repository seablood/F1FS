package kr.co.F1FS.app.domain.reply.domain;

import jakarta.persistence.*;
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
@Table(name = "reply_comment")
public class ReplyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;
    @ManyToOne
    @JoinColumn(name = "reply_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Reply reply;
    @Lob
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updatedAt;

    public void modify(String content){
        this.content = content;
    }

    @Builder
    public ReplyComment(String content, User user, Reply reply){
        this.content = content;
        this.user = user;
        this.reply = reply;
    }
}
