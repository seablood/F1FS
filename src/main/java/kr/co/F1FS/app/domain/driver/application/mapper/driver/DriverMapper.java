package kr.co.F1FS.app.domain.driver.application.mapper.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.CreateDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {
    public Driver toDriver(CreateDriverDTO dto){
        return Driver.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .number(dto.getNumber())
                .team(dto.getConstructorName())
                .championships(dto.getChampionships())
                .country(dto.getCountry())
                .followerNum(dto.getFollowerNum())
                .birth(dto.getBirth())
                .racingClass(dto.getRacingClass())
                .build();
    }

    public ResponseDriverDTO toResponseDriverDTO(Driver driver, ResponseCurrentSeasonDTO seasonDTO,
                                                 ResponseSinceDebutDTO debutDTO){
        return ResponseDriverDTO.builder()
                .name(driver.getName())
                .engName(driver.getEngName())
                .number(driver.getNumber())
                .championships(driver.getChampionships())
                .country(driver.getCountry())
                .followerNum(driver.getFollowerNum())
                .birth(driver.getBirth())
                .constructorName(driver.getTeam())
                .currentSeason(seasonDTO)
                .sinceDebut(debutDTO)
                .build();
    }

    public SimpleResponseDriverDTO toSimpleResponseDriverDTO(Driver driver){
        return SimpleResponseDriverDTO.builder()
                .id(driver.getId())
                .name(driver.getName())
                .engName(driver.getEngName())
                .number(driver.getNumber())
                .team(driver.getTeam())
                .racingClass(driver.getRacingClass())
                .build();
    }
}
