package kr.co.F1FS.app.domain.model.rdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_complain")
public class PostComplain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "to_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post toPost;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User fromUser;
    // 신고 사유
    private String description;
    // 사유 부연 설명(신고자 직접 작성)
    private String paraphrase;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @Builder
    public PostComplain(Post toPost, User fromUser, String description, String paraphrase){
        this.toPost = toPost;
        this.fromUser = fromUser;
        this.description = description;
        this.paraphrase = paraphrase;
        this.createdAt = LocalDateTime.now();
    }
}
