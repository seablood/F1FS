package kr.co.F1FS.app.domain.follow.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "follow_constructor")
public class FollowConstructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followerUser;

    @ManyToOne
    @JoinColumn(name = "followee_constructor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Constructor followeeConstructor;

    @Builder
    public FollowConstructor(User followerUser, Constructor followeeConstructor){
        this.followerUser = followerUser;
        this.followeeConstructor = followeeConstructor;
    }
}
