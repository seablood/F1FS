package kr.co.F1FS.app.domain.driver.application.service.driver;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.debut.CreateDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.record.CreateDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.CreateDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.driver.DriverJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.CreateDriverDTO;
import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.CreateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.CreateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import kr.co.F1FS.app.domain.team.application.port.in.admin.CreateConstructorDriverRelationUseCase;
import kr.co.F1FS.app.global.application.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDriverService implements CreateDriverUseCase {
    private final DriverJpaPort driverJpaPort;
    private final DriverDomainService driverDomainService;
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final CreateCurrentSeasonUseCase createCurrentSeasonUseCase;
    private final CreateSinceDebutUseCase createSinceDebutUseCase;
    private final CreateConstructorDriverRelationUseCase createCDRelationUseCase;
    private final CreateDriverRecordRelationUseCase createDriverRecordRelationUseCase;
    private final CreateDriverDebutRelationUseCase createDriverDebutRelationUseCase;
    private final ValidationService validationService;

    @Override
    public Driver save(CreateDriverDTO driverDTO, CreateCurrentSeasonDTO currentSeasonDTO, CreateSinceDebutDTO sinceDebutDTO) {
        Driver driver = driverDomainService.createEntity(driverDTO);
        validationService.checkValid(driver);
        CurrentSeason currentSeason = createCurrentSeasonUseCase.save(currentSeasonDTO);
        SinceDebut sinceDebut = createSinceDebutUseCase.save(sinceDebutDTO);

        if(!driver.getTeam().equals("FA")){
            Constructor constructor = queryConstructorUseCase.findByName(driver.getTeam());
            driverDomainService.updateEngTeam(driver, constructor.getEngName());

            createCDRelationUseCase.save(constructor, driver);
        }

        createDriverRecordRelationUseCase.save(driver, currentSeason);
        createDriverDebutRelationUseCase.save(driver, sinceDebut);

        return driverJpaPort.save(driver);
    }
}
