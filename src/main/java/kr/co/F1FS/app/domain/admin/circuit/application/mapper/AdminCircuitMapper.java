package kr.co.F1FS.app.domain.admin.circuit.application.mapper;

import kr.co.F1FS.app.domain.admin.circuit.presentation.dto.CreateCircuitDTO;
import kr.co.F1FS.app.domain.admin.circuit.presentation.dto.ModifyCircuitDTO;
import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import org.springframework.stereotype.Component;

@Component
public class AdminCircuitMapper {
    public CreateCircuitCommand toCreateCircuitCommand(CreateCircuitDTO dto){
        return CreateCircuitCommand.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .length(dto.getLength())
                .first_grand_prix(dto.getFirst_grand_prix())
                .laps(dto.getLaps())
                .fastestLap(dto.getFastestLap())
                .raceDistance(dto.getRaceDistance())
                .build();
    }

    public ModifyCircuitCommand toModifyCircuitCommand(ModifyCircuitDTO dto){
        return ModifyCircuitCommand.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .length(dto.getLength())
                .first_grand_prix(dto.getFirst_grand_prix())
                .laps(dto.getLaps())
                .fastestLap(dto.getFastestLap())
                .raceDistance(dto.getRaceDistance())
                .build();
    }
}
