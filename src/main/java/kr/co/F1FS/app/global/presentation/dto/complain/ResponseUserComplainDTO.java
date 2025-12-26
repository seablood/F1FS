package kr.co.F1FS.app.global.presentation.dto.complain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserComplainDTO {
    private Long id;
    private String toUserNickname;
    private String fromUserNickname;
    private String description;
    private String paraphrase;
}
