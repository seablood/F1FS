package kr.co.F1FS.app.global.presentation.dto.circuit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCircuitDTO {
    private String name;
    private String engName;
    private Double length;
    private Integer first_grand_prix;
    private Integer laps;
    private String fastestLap;
    private Double raceDistance;
}
