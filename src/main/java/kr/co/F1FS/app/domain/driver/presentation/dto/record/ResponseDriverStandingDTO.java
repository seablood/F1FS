package kr.co.F1FS.app.domain.driver.presentation.dto.record;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseDriverStandingDTO {
    private String driverName;
    private String constructorName;
    private Integer points;

    @QueryProjection
    public ResponseDriverStandingDTO(String driverName, String constructorName, Integer points){
        this.driverName = driverName;
        this.constructorName = constructorName;
        this.points = points;
    }
}
