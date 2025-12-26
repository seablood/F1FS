package kr.co.F1FS.app.domain.circuit.application.mapper.admin;

import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.CreateCircuitDTO;
import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.ModifyCircuitDTO;
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
                .fastestLapDriver(dto.getFastestLapDriver())
                .fastestLapSeason(dto.getFastestLapSeason())
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
                .fastestLapDriver(dto.getFastestLapDriver())
                .fastestLapSeason(dto.getFastestLapSeason())
                .raceDistance(dto.getRaceDistance())
                .build();
    }
}
