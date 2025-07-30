package kr.co.F1FS.app.global.presentation.dto.driver;

import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseDriverDTO {
    private Long id;
    private String name;
    private String engName;
    private Integer number;
    private String team;
    private RacingClass racingClass;
}
