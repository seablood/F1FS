package kr.co.F1FS.app.domain.post.domain;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted = true WHERE id = ?")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "block_order")
    private List<PostBlock> blocks = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updatedAt;
    private int likeNum;
    @Column(nullable = false)
    private boolean deleted = false;

    public void update(String title, List<PostBlock> newBlocks){
        this.title = title;
        this.blocks.clear();

        for (PostBlock block : newBlocks){
            addBlock(block);
        }
    }

    public void addBlock(PostBlock block){
        block.setPost(this);
        this.blocks.add(block);
    }

    public void increaseLike(){
        this.likeNum++;
    }

    public void decreaseLike(){
        this.likeNum--;
    }

    @Builder
    public Post(String title, User author){
        this.title = title;
        this.author = author;
        this.likeNum = 0;
    }
}
