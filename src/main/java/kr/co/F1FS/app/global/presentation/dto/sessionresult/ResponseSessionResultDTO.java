package kr.co.F1FS.app.global.presentation.dto.sessionresult;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.RaceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSessionResultDTO {
    private String driverName;
    private String constructorName;
    private Integer position;
    private String timeOrGap;
    private Integer points;
    private boolean isFastestLap;
    @Enumerated(value = EnumType.STRING)
    private RaceStatus raceStatus;
}
