package kr.co.F1FS.app.domain.post.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_like_relation")
public class PostLikeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public PostLikeRelation(User user, Post post){
        this.user = user;
        this.post = post;
    }
}
