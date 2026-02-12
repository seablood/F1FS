package kr.co.F1FS.app.global.presentation.dto.grandprix;

import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGrandPrixDTO {
    private String name;
    private String engName;
    private SimpleResponseSessionDTO firstPractice;
    private SimpleResponseSessionDTO secondPractice;
    private SimpleResponseSessionDTO thirdPractice;
    private SimpleResponseSessionDTO sprintQualifying;
    private SimpleResponseSessionDTO qualifying;
    private SimpleResponseSessionDTO sprint;
    private SimpleResponseSessionDTO race;
    private String description;
    private SimpleResponseCircuitDTO circuit;
}
