package kr.co.F1FS.app.domain.follow.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFollowUserDTO {
    private String username;
    private String nickname;
}
