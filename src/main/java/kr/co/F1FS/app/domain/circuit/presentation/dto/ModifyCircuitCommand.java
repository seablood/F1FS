package kr.co.F1FS.app.domain.circuit.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCircuitCommand {
    private String name;
    private String engName;
    private Double length;
    private Integer first_grand_prix;
    private Integer laps;
    private String fastestLap;
    private Double raceDistance;
}
