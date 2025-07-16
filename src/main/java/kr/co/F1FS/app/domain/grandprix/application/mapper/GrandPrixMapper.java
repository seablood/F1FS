package kr.co.F1FS.app.domain.grandprix.application.mapper;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class GrandPrixMapper {
    public GrandPrix toGrandPrix(CreateGrandPrixCommand command){
        return GrandPrix.builder()
                .name(command.getName())
                .engName(command.getEngName())
                .firstPracticeTime(command.getFirstPracticeTime())
                .secondPracticeTime(command.getSecondPracticeTime())
                .thirdPracticeTime(command.getThirdPracticeTime())
                .sprintQualifyingTime(command.getSprintQualifyingTime())
                .qualifyingTime(command.getQualifyingTime())
                .sprintTime(command.getSprintTime())
                .raceTime(command.getRaceTime())
                .description(command.getDescription())
                .circuitId(command.getCircuitId())
                .season(command.getSeason())
                .round(command.getRound())
                .build();
    }

    public ResponseGrandPrixDTO toResponseGrandPrixDTO(GrandPrix grandPrix, SimpleResponseCircuitDTO circuitDTO){
        return ResponseGrandPrixDTO.builder()
                .name(grandPrix.getName())
                .engName(grandPrix.getEngName())
                .firstPracticeTime(grandPrix.getFirstPracticeTime())
                .secondPracticeTime(grandPrix.getSecondPracticeTime())
                .thirdPracticeTime(grandPrix.getThirdPracticeTime())
                .sprintQualifyingTime(grandPrix.getSprintQualifyingTime())
                .qualifyingTime(grandPrix.getQualifyingTime())
                .sprintTime(grandPrix.getSprintTime())
                .raceTime(grandPrix.getRaceTime())
                .description(grandPrix.getDescription())
                .circuit(circuitDTO)
                .build();
    }

    public SimpleResponseGrandPrixDTO toSimpleResponseGrandPrixDTO(GrandPrix grandPrix){
        return SimpleResponseGrandPrixDTO.builder()
                .name(grandPrix.getName())
                .engName(grandPrix.getEngName())
                .raceWeek(TimeUtil.getRaceDay(grandPrix.getFirstPracticeTime(), grandPrix.getRaceTime()))
                .isThisWeek(grandPrix.isThisWeek())
                .build();
    }
}
