package kr.co.F1FS.app.domain.admin.driver.application.mapper;

import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.ModifyDriverDTO;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.ModifyDriverCommand;
import org.springframework.stereotype.Component;

@Component
public class AdminDriverMapper {
    public AdminResponseDriverDTO toAdminResponseDriverDTO(Driver driver){
        return AdminResponseDriverDTO.builder()
                .id(driver.getId())
                .name(driver.getName())
                .engName(driver.getEngName())
                .number(driver.getNumber())
                .team(driver.getTeam())
                .engTeam(driver.getEngTeam())
                .championships(driver.getChampionships())
                .country(driver.getCountry())
                .followerNum(driver.getFollowerNum())
                .birth(driver.getBirth())
                .racingClass(driver.getRacingClass().toString())
                .build();
    }

    public ModifyDriverCommand toModifyDriverCommand(ModifyDriverDTO dto){
        return ModifyDriverCommand.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .number(dto.getNumber())
                .championships(dto.getChampionships())
                .country(dto.getCountry())
                .birth(dto.getBirth())
                .build();
    }
}
