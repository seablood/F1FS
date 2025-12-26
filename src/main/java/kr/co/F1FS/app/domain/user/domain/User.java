package kr.co.F1FS.app.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import kr.co.F1FS.app.global.util.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    @Column(length = 500)
    private String description;
    @CreationTimestamp
    private Timestamp createDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp lastLoginDate;

    // 징계 관련 필드
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp suspendUntil;
    private Integer suspendNum;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateLastLoginDate(){
        LocalDateTime now = LocalDateTime.now();
        this.lastLoginDate = Timestamp.valueOf(now);
    }

    public void updateSuspendUntil(Integer durationDays){
        this.suspendUntil = Timestamp.valueOf(LocalDateTime.now().plusDays(durationDays));
    }

    public boolean isSuspendUntil(){
        return LocalDateTime.now().isBefore(suspendUntil.toLocalDateTime());
    }

    public void updateRole(Role role){
        this.role = role;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void changeFollowerNum(int i){
        this.followerNum +=i;
    }

    public void changeFollowingNum(int i){
        this.followingNum +=i;
    }

    public void updateSuspendNum(){
        this.suspendNum += 1;
    }

    @Builder
    public User(String username, String password, String nickname, String email, String providerId, String provider,
                String description){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.providerId = providerId;
        this.provider = provider;
        this.description = description;
        this.followerNum = 0;
        this.followingNum = 0;
        this.suspendNum = 0;
    }
}
