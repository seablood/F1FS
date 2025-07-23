package kr.co.F1FS.app.domain.driver.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDriverStandingDTO {
    private String driverName;
    private String constructorName;
    private Integer points;
}
