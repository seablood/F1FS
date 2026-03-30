package kr.co.F1FS.app.global.presentation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDocumentDTO {
    private Long id;
    private String username;
    private String nickname;
    private int followerNum;
}
