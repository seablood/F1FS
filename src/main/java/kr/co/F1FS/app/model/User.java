package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import kr.co.F1FS.app.util.Role;
import lombok.*;

@Entity
@Data
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
    private String nickname;
    private String email;
    private String providerId;
    private String provider;
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;
    @Column(length = 400)
    private String refreshToken;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    @Builder
    public User(String username, String password, String nickname, String email, String providerId, String provider){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.providerId = providerId;
        this.provider = provider;
    }
}
