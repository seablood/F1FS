package kr.co.F1FS.app.domain.driver.application.service.driver;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.debut.CheckDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.debut.CreateDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.UpdateDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.record.CreateDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.driver.DriverJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.ModifyDriverCommand;
import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.CreateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.CreateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDriverService implements UpdateDriverUseCase {
    private final DriverJpaPort driverJpaPort;
    private final DriverDomainService driverDomainService;
    private final CheckDriverDebutRelationUseCase checkDriverDebutRelationUseCase;
    private final CreateCurrentSeasonUseCase createCurrentSeasonUseCase;
    private final CreateSinceDebutUseCase createSinceDebutUseCase;
    private final CreateDriverRecordRelationUseCase createDriverRecordRelationUseCase;
    private final CreateDriverDebutRelationUseCase createDriverDebutRelationUseCase;

    @Override
    public void modify(Driver driver, ModifyDriverCommand command) {
        driverDomainService.modify(driver, command);
        driverJpaPort.saveAndFlush(driver);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "DriverDTO", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverByNumber", key = "#driver.number", cacheManager = "redisLongCacheManager")
    })
    public void transfer(Driver driver, Constructor constructor) {
        RacingClass racingClass = constructor.getRacingClass();

        driverDomainService.updateTeam(driver, constructor.getName(), constructor.getEngName());
        driverDomainService.updateRacingClass(driver, racingClass);

        if(!checkDriverDebutRelationUseCase.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass)){
            CurrentSeason currentSeason = createCurrentSeasonUseCase.save();
            SinceDebut sinceDebut = createSinceDebutUseCase.save();

            createDriverRecordRelationUseCase.save(driver, currentSeason);
            createDriverDebutRelationUseCase.save(driver, sinceDebut);
        }

        driverJpaPort.saveAndFlush(driver);
    }

    @Override
    public void increaseFollower(Driver driver) {
        driverDomainService.increaseFollower(driver);

        driverJpaPort.saveAndFlush(driver);
    }

    @Override
    public void decreaseFollower(Driver driver) {
        driverDomainService.decreaseFollower(driver);

        driverJpaPort.saveAndFlush(driver);
    }
}
