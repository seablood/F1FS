package kr.co.F1FS.app.domain.complain.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_complain")
public class UserComplain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User fromUser;
    // 신고 사유
    private String description;
    // 사유 부연 설명(신고자 직접 작성)
    @Column(length = 500)
    private String paraphrase;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @Builder
    public UserComplain(User toUser, User fromUser, String description, String paraphrase){
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.description = description;
        this.paraphrase = paraphrase;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
