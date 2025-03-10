package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import kr.co.F1FS.app.dto.ModifyPostDTO;
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
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;
    @ManyToOne
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

    public void modify(ModifyPostDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void increaseLike(){
        this.likeNum++;
    }

    public void decreaseLike(){
        this.likeNum--;
    }

    @Builder
    public Post(String title, String content, User author){
        this.title = title;
        this.content = content;
        this.author = author;
        this.likeNum = 0;
    }
}
