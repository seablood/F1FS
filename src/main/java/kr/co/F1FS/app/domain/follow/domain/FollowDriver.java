package kr.co.F1FS.app.domain.follow.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "follow_driver")
public class FollowDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followerUser;

    @ManyToOne
    @JoinColumn(name = "followee_driver_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Driver followeeDriver;

    @Builder
    public FollowDriver(User followerUser, Driver followeeDriver){
        this.followerUser = followerUser;
        this.followeeDriver = followeeDriver;
    }
}
