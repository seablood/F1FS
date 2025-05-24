package kr.co.F1FS.app.domain.model.rdb;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User fromUser;
    // 신고 사유
    private String description;
    // 사유 부연 설명(신고자 직접 작성)
    private String paraphrase;

    @Builder
    public UserComplain(User toUser, User fromUser, String description, String paraphrase){
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.description = description;
        this.paraphrase = paraphrase;
    }
}
