package kr.co.F1FS.app.domain.admin.constructor.application.mapper;

import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.ModifyConstructorDTO;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ModifyConstructorCommand;
import org.springframework.stereotype.Component;

@Component
public class AdminConstructorMapper {
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

    public ModifyConstructorCommand toModifyConstructorCommand(ModifyConstructorDTO dto){
        return ModifyConstructorCommand.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .championships(dto.getChampionships())
                .base(dto.getBase())
                .teamChief(dto.getTeamChief())
                .chassis(dto.getChassis())
                .powerUnit(dto.getPowerUnit())
                .build();
    }
}
