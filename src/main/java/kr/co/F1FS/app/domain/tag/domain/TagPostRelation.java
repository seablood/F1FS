package kr.co.F1FS.app.domain.tag.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tag_post_relation")
public class TagPostRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tag tag;

    @Builder
    public TagPostRelation(Post post, Tag tag){
        this.post = post;
        this.tag = tag;
    }
}
