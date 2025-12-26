package kr.co.F1FS.app.domain.constructor.application.mapper.admin;

import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.ModifyConstructorDTO;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.ModifyConstructorCommand;
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
