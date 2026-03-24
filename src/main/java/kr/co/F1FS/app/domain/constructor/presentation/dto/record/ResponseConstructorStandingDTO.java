package kr.co.F1FS.app.domain.constructor.presentation.dto.record;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseConstructorStandingDTO {
    private String constructorName;
    private Integer points;

    @QueryProjection
    public ResponseConstructorStandingDTO(String constructorName, Integer points){
        this.constructorName = constructorName;
        this.points = points;
    }
}
