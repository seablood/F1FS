package kr.co.F1FS.app.domain.grandprix.application.mapper;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseSessionDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.SessionType;
import kr.co.F1FS.app.global.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class GrandPrixMapper {
    public GrandPrix toGrandPrix(CreateGrandPrixCommand command){
        return GrandPrix.builder()
                .name(command.getName())
                .engName(command.getEngName())
                .firstSessionTime(command.getFirstPracticeTime())
                .lastSessionTime(command.getRaceTime())
                .firstPracticeTime(TimeUtil.getSessionTime(command.getFirstPracticeTime()))
                .secondPracticeTime(TimeUtil.getSessionTime(command.getSecondPracticeTime()))
                .thirdPracticeTime(TimeUtil.getSessionTime(command.getThirdPracticeTime()))
                .sprintQualifyingTime(TimeUtil.getSessionTime(command.getSprintQualifyingTime()))
                .qualifyingTime(TimeUtil.getSessionTime(command.getQualifyingTime()))
                .sprintTime(TimeUtil.getSessionTime(command.getSprintTime()))
                .raceTime(TimeUtil.getSessionTime(command.getRaceTime()))
                .description(command.getDescription())
                .circuitId(command.getCircuitId())
                .season(command.getSeason())
                .round(command.getRound())
                .racingClass(RacingClass.valueOf(command.getRacingClass()))
                .build();
    }

    public ResponseGrandPrixDTO toResponseGrandPrixDTO(GrandPrix grandPrix, SimpleResponseCircuitDTO circuitDTO){
        return ResponseGrandPrixDTO.builder()
                .name(grandPrix.getName())
                .engName(grandPrix.getEngName())
                .firstPractice(toSimpleResponseSessionDTO(grandPrix.getFirstPractice(), SessionType.PRACTICE_1, grandPrix.getFirstPracticeTime()))
                .secondPractice(toSimpleResponseSessionDTO(grandPrix.getSecondPractice(), SessionType.PRACTICE_2, grandPrix.getSecondPracticeTime()))
                .thirdPractice(toSimpleResponseSessionDTO(grandPrix.getThirdPractice(), SessionType.PRACTICE_3, grandPrix.getThirdPracticeTime()))
                .sprintQualifying(toSimpleResponseSessionDTO(grandPrix.getSprintQualifying(), SessionType.SPRINT_QUALIFYING, grandPrix.getSprintQualifyingTime()))
                .qualifying(toSimpleResponseSessionDTO(grandPrix.getQualifying(), SessionType.QUALIFYING, grandPrix.getQualifyingTime()))
                .sprint(toSimpleResponseSessionDTO(grandPrix.getSprint(), SessionType.SPRINT_RACE, grandPrix.getSprintTime()))
                .race(toSimpleResponseSessionDTO(grandPrix.getRace(), SessionType.RACE, grandPrix.getRaceTime()))
                .description(grandPrix.getDescription())
                .circuit(circuitDTO)
                .build();
    }

    public SimpleResponseGrandPrixDTO toSimpleResponseGrandPrixDTO(GrandPrix grandPrix){
        return SimpleResponseGrandPrixDTO.builder()
                .name(grandPrix.getName())
                .engName(grandPrix.getEngName())
                .raceWeek(TimeUtil.getGrandPrixDay(grandPrix.getFirstSessionTime(), grandPrix.getLastSessionTime()))
                .isThisWeek(grandPrix.isThisWeek())
                .build();
    }

    public SimpleResponseSessionDTO toSimpleResponseSessionDTO(Long id, SessionType sessionType, String time){
        if(id == null || time == null) return null;
        return SimpleResponseSessionDTO.builder()
                .id(id)
                .sessionType(sessionType)
                .time(time)
                .build();
    }
}
