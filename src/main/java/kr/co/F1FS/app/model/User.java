package kr.co.F1FS.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import kr.co.F1FS.app.util.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    @Size(min = 1, max = 20, message = "nickname은 20자 이하로 설정해주세요.")
    private String nickname;
    @Column(unique = true)
    private String email;
    private Integer followerNum;
    private Integer followingNum;
    private String providerId;
    private String provider;
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.TEMPORARY;
    @Column(length = 400)
    private String refreshToken;
    @CreationTimestamp
    private Timestamp createDate;
    private Date lastLoginDate;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateLastLoginDate(){
        Date now = new Date();
        this.lastLoginDate = now;
    }

    public void updateRole(Role role){
        this.role = role;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void changeFollowerNum(int i){
        this.followerNum +=i;
    }

    public void changeFollowingNum(int i){
        this.followingNum +=i;
    }

    @Builder
    public User(String username, String password, String nickname, String email, String providerId, String provider){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.providerId = providerId;
        this.provider = provider;
        this.followerNum = 0;
        this.followingNum = 0;
    }
}
