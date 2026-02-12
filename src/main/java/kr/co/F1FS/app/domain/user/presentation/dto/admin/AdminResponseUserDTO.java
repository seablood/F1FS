package kr.co.F1FS.app.domain.user.presentation.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class AdminResponseUserDTO {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private Integer followerNum;
    private Integer followingNum;
    private String providerId;
    private String provider;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp lastLoginDate;
}
