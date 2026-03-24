package kr.co.F1FS.app.domain.reply.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "replies")
@SQLDelete(sql = "UPDATE replies SET deleted = true WHERE id = ?")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggest_id")
    private Suggest suggest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
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
    private int likeNum;
    @Column(nullable = false)
    private boolean deleted = false;

    public void modify(String content){
        this.content = content;
    }

    public void increaseLikeNum(){
        this.likeNum++;
    }

    public void decreaseLikeNum(){
        this.likeNum--;
    }

    @Builder
    public Reply(String content, Post post, Suggest suggest, User user){
        this.content = content;
        this.post = post;
        this.suggest = suggest;
        this.user = user;
        this.likeNum = 0;
    }
}
