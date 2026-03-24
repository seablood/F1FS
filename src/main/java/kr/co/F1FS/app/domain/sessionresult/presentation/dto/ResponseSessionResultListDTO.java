package kr.co.F1FS.app.domain.sessionresult.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.RaceStatus;
import lombok.Data;

@Data
public class ResponseSessionResultListDTO {
    private String driverName;
    private String constructorName;
    private Integer position;
    private String timeOrGap;
    private Integer points;
    private boolean isFastestLap;
    @Enumerated(value = EnumType.STRING)
    private RaceStatus raceStatus;

    @QueryProjection
    public ResponseSessionResultListDTO(String driverName, String constructorName, Integer position, String timeOrGap,
                                        Integer points, boolean isFastestLap, RaceStatus raceStatus){
        this.driverName = driverName;
        this.constructorName = constructorName;
        this.position = position;
        this.timeOrGap = timeOrGap;
        this.points = points;
        this.isFastestLap = isFastestLap;
        this.raceStatus = raceStatus;
    }
}
