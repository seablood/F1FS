package kr.co.F1FS.app.domain.post.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.global.util.PostBlockType;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_block")
public class PostBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Enumerated(value = EnumType.STRING)
    private PostBlockType blockType;

    public void setPost(Post post){
        this.post = post;
    }

    @Builder
    public PostBlock(String content, PostBlockType blockType){
        this.content = content;
        this.blockType = blockType;
    }
}
