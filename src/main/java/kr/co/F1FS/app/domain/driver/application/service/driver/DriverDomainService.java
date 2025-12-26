package kr.co.F1FS.app.domain.driver.application.service.driver;

import kr.co.F1FS.app.domain.driver.application.mapper.driver.DriverMapper;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.CreateDriverDTO;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.ModifyDriverCommand;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDomainService {
    private final DriverMapper driverMapper;

    public Driver createEntity(CreateDriverDTO driverDTO){
        return driverMapper.toDriver(driverDTO);
    }

    public void modify(Driver driver, ModifyDriverCommand command){
        driver.modify(command);
    }

    public void updateTeam(Driver driver, String constructorName, String constructorEngName){
        driver.updateTeam(constructorName, constructorEngName);
    }

    public void updateEngTeam(Driver driver, String engTeam){
        driver.updateEngTeam(engTeam);
    }

    public void updateRacingClass(Driver driver, RacingClass racingClass){
        driver.updateRacingClass(racingClass);
    }

    public void increaseFollower(Driver driver){
        driver.increaseFollower();
    }

    public void decreaseFollower(Driver driver){
        driver.decreaseFollower();
    }
}
