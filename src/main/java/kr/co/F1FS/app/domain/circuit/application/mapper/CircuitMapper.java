package kr.co.F1FS.app.domain.circuit.application.mapper;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import kr.co.F1FS.app.global.util.LapMillisUtil;
import org.springframework.stereotype.Component;

@Component
public class CircuitMapper {
    public Circuit toCircuit(CreateCircuitCommand command){
        return Circuit.builder()
                .name(command.getName())
                .engName(command.getEngName())
                .length(command.getLength())
                .laps(command.getLaps())
                .first_grand_prix(command.getFirst_grand_prix())
                .fastestLap(LapMillisUtil.fastestToMillis(command.getFastestLap()))
                .raceDistance(command.getRaceDistance())
                .build();
    }

    public ResponseCircuitDTO toResponseCircuitDTO(Circuit circuit){
        return  ResponseCircuitDTO.builder()
                .name(circuit.getName())
                .engName(circuit.getEngName())
                .length(circuit.getLength())
                .laps(circuit.getLaps())
                .first_grand_prix(circuit.getFirst_grand_prix())
                .fastestLap(LapMillisUtil.fastestLapFormat(circuit.getFastestLap()))
                .raceDistance(circuit.getRaceDistance())
                .build();
    }

    public SimpleResponseCircuitDTO toSimpleResponseCircuitDTO(Circuit circuit){
        return SimpleResponseCircuitDTO.builder()
                .id(circuit.getId())
                .name(circuit.getName())
                .engName(circuit.getEngName())
                .build();
    }
}
