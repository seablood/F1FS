package kr.co.F1FS.app.domain.admin.grandprix.application.mapper;

import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.ModifyGrandPrixDTO;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import org.springframework.stereotype.Component;

@Component
public class AdminGrandPrixMapper {
    public CreateGrandPrixCommand toCreateGrandPrixCommand(CreateGrandPrixDTO dto){
        return CreateGrandPrixCommand.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .firstPracticeTime(dto.getFirstPracticeTime())
                .secondPracticeTime(dto.getSecondPracticeTime())
                .thirdPracticeTime(dto.getThirdPracticeTime())
                .sprintQualifyingTime(dto.getSprintQualifyingTime())
                .qualifyingTime(dto.getQualifyingTime())
                .sprintTime(dto.getSprintTime())
                .raceTime(dto.getRaceTime())
                .description(dto.getDescription())
                .circuitId(dto.getCircuitId())
                .season(dto.getSeason())
                .round(dto.getRound())
                .racingClass(dto.getRacingClass())
                .build();
    }

    public ModifyGrandPrixCommand toModifyGrandPrixCommand(ModifyGrandPrixDTO dto){
        return ModifyGrandPrixCommand.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .firstPracticeTime(dto.getFirstPracticeTime())
                .secondPracticeTime(dto.getSecondPracticeTime())
                .thirdPracticeTime(dto.getThirdPracticeTime())
                .sprintQualifyingTime(dto.getSprintQualifyingTime())
                .qualifyingTime(dto.getQualifyingTime())
                .sprintTime(dto.getSprintTime())
                .raceTime(dto.getRaceTime())
                .description(dto.getDescription())
                .circuitId(dto.getCircuitId())
                .build();
    }
}
