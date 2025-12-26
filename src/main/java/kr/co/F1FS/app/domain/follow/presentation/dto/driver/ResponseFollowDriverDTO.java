package kr.co.F1FS.app.domain.follow.presentation.dto.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFollowDriverDTO {
    private String name;
    private String engName;
    private Integer number;
    private String team;
}
