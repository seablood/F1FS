package kr.co.F1FS.app.domain.admin.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseUserComplainDTO {
    private Long id;
    private String toUserNickname;
    private String fromUserNickname;
    private String description;
    private String paraphrase;
}
