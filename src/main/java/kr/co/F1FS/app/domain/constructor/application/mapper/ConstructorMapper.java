package kr.co.F1FS.app.domain.constructor.application.mapper;

import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConstructorMapper {
    public AdminResponseConstructorDTO toAdminResponseConstructorDTO(Constructor constructor){
        return AdminResponseConstructorDTO.builder()
                .id(constructor.getId())
                .name(constructor.getName())
                .engName(constructor.getEngName())
                .championships(constructor.getChampionships())
                .base(constructor.getBase())
                .teamChief(constructor.getTeamChief())
                .chassis(constructor.getChassis())
                .powerUnit(constructor.getPowerUnit())
                .followerNum(constructor.getFollowerNum())
                .racingClass(constructor.getRacingClass().toString())
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
