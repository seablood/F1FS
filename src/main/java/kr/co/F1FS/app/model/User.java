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
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    public User(String username, String password, String nickname, String email){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}
