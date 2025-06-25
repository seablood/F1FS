package kr.co.F1FS.app.domain.reply.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.post.domain.Post;
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
@Table(name = "replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "suggest_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Suggest suggest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

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
    public Reply(String content, Post post, Suggest suggest, User user){
        this.content = content;
        this.post = post;
        this.suggest = suggest;
        this.user = user;
    }
}
