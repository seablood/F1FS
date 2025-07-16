package kr.co.F1FS.app.global.presentation.dto.grandprix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseGrandPrixDTO {
    private String name;
    private String engName;
    private String raceWeek;
    private boolean isThisWeek;
}
