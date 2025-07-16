package kr.co.F1FS.app.domain.sessionresult.presentation.dto;

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
public class CreateSessionResultCommand {
    private String driverName;
    private String constructorName;
    private Integer position;
    private String timeOrGap;
    private Integer points;
    private boolean isFastestLap;
    @Enumerated(value = EnumType.STRING)
    private RaceStatus raceStatus;
}
