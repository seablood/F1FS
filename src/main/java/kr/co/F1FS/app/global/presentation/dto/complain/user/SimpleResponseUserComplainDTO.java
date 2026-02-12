package kr.co.F1FS.app.global.presentation.dto.complain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseUserComplainDTO {
    private Long id;
    private String toUserNickname;
    private String description;
}
