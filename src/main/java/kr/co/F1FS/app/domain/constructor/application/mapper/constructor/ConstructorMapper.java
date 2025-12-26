package kr.co.F1FS.app.domain.constructor.application.mapper.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.CreateConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConstructorMapper {
    public Constructor toConstructor(CreateConstructorDTO dto){
        return Constructor.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .championships(dto.getChampionships())
                .base(dto.getBase())
                .teamChief(dto.getTeamChief())
                .chassis(dto.getChassis())
                .powerUnit(dto.getPowerUnit())
                .followerNum(dto.getFollowerNum())
                .racingClass(dto.getRacingClass())
                .build();
    }

    public SimpleResponseConstructorDTO toSimpleResponseConstructorDTO(Constructor constructor){
        return SimpleResponseConstructorDTO.builder()
                .id(constructor.getId())
                .korName(constructor.getName())
                .engName(constructor.getEngName())
                .racingClass(constructor.getRacingClass().toString())
                .build();
    }

    public ResponseConstructorDTO toResponseConstructorDTO(Constructor constructor, List<String> drivers,
                                                           ResponseCurrentSeasonDTO currentSeasonDTO,
                                                           ResponseSinceDebutDTO sinceDebutDTO){
        return ResponseConstructorDTO.builder()
                .name(constructor.getName())
                .engName(constructor.getEngName())
                .drivers(drivers)
                .championships(constructor.getChampionships())
                .base(constructor.getBase())
                .teamChief(constructor.getTeamChief())
                .chassis(constructor.getChassis())
                .powerUnit(constructor.getPowerUnit())
                .followerNum(constructor.getFollowerNum())
                .currentSeason(currentSeasonDTO)
                .sinceDebut(sinceDebutDTO)
                .build();
    }
}
