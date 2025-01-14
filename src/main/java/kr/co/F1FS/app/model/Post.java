package kr.co.F1FS.app.model;

import jakarta.persistence.*;
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

    @Builder
    public Post(String title, String content, User author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
