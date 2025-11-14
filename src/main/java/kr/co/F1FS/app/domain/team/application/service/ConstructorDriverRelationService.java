package kr.co.F1FS.app.domain.team.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.SinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.team.application.mapper.ConstructorDriverRelationMapper;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.*;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationService implements ConstructorDriverRelationUseCase {
    private final DriverUseCase driverUseCase;
    private final DriverRecordRelationUseCase driverRecordUseCase;
    private final DriverDebutRelationUseCase driverDebutUseCase;
    private final DriverDebutRelationUseCase driverDebutRelationUseCase;
    private final ConstructorUseCase constructorUseCase;
    private final CDRelationJpaPort cdRelationJpaPort;
    private final CurrentSeasonUseCase currentSeasonUseCase;
    private final SinceDebutUseCase sinceDebutUseCase;
    private final ConstructorDriverRelationMapper relationMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ConstructorDriverRelation save(Constructor constructor, Driver driver){
        ConstructorDriverRelation relation = relationMapper.toConstructorDriverRelation(constructor, driver);
        return cdRelationJpaPort.save(relation);
    }

    @Override
    public List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor) {
        return cdRelationJpaPort.findConstructorDriverRelationByConstructor(constructor);
    }

    @Override
    @Transactional
    public void transfer(Integer number, String constructorName){
        Driver driver = driverUseCase.findByNumberNotDTONotCache(number);
        Constructor constructor = constructorUseCase.findByNameNotDTONotCache(constructorName);
        cacheEvictUtil.evictCachingDriver(driver);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(cdRelationJpaPort.existsConstructorDriverRelationByDriverAndRacingClass(driver, constructor.getRacingClass())){
            if(cdRelationJpaPort.existsConstructorDriverRelationByDriverAndConstructor(driver, constructor)){
                driverUseCase.updateTeam(driver, constructor.getName(), constructor.getEngName());
                modifyRacingClass(driver, constructor.getRacingClass());
            } else {
                ConstructorDriverRelation relation = cdRelationJpaPort.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, constructor.getRacingClass()
                );
                relation.updateConstructor(constructor);
                driverUseCase.updateTeam(driver, constructor.getName(), constructor.getEngName());
                modifyRacingClass(driver, constructor.getRacingClass());

                cdRelationJpaPort.saveAndFlush(relation);
            }
        } else {
            ConstructorDriverRelation relation = ConstructorDriverRelation.builder()
                    .constructor(constructor)
                    .driver(driver)
                    .racingClass(constructor.getRacingClass())
                    .build();
            driverUseCase.updateTeam(driver, constructor.getName(), constructor.getEngName());
            modifyRacingClass(driver, constructor.getRacingClass());

            driverUseCase.saveAndFlush(driver);
            cdRelationJpaPort.saveAndFlush(relation);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "DriverDTO", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverByNumber", key = "#driver.number", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverCurrentSeason", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverSinceDebut", key = "#driver.id", cacheManager = "redisLongCacheManager")
    })
    public void modifyRacingClass(Driver driver, RacingClass racingClass){
        driverUseCase.updateRacingClass(driver, racingClass);

        if(!driverDebutRelationUseCase.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass)){
            CurrentSeason currentSeason = new CurrentSeason();
            SinceDebut sinceDebut = new SinceDebut();

            driverRecordUseCase.save(driver, currentSeason);
            driverDebutUseCase.save(driver, sinceDebut);
            currentSeasonUseCase.save(currentSeason);
            sinceDebutUseCase.save(sinceDebut);
        }
    }

    @Override
    @Transactional
    public void delete(Long id, String option) {
        Driver driver = driverUseCase.findByIdNotDTONotCache(id);
        cacheEvictUtil.evictCachingDriver(driver);

        option(driver, option);
    }

    @Override
    public void option(Driver driver, String option){
        ConstructorDriverRelation relation;

        switch (option){
            case "currentTeam" :
                relation = cdRelationJpaPort.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, driver.getRacingClass()
                );
                cacheEvictUtil.evictCachingConstructor(relation.getConstructor());
                cdRelationJpaPort.delete(relation);
                break;

            case "F1", "F2", "F3", "F1_ACADEMY", "RESERVE" :
                RacingClass racingClass = RacingClass.valueOf(option);
                relation = cdRelationJpaPort.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, racingClass
                );
                cacheEvictUtil.evictCachingConstructor(relation.getConstructor());
                cdRelationJpaPort.delete(relation);
                break;

            case "all" :
                List<ConstructorDriverRelation> list = cdRelationJpaPort.findConstructorDriverRelationByDriver(driver);
                list.stream().forEach(relation1 -> {
                    cacheEvictUtil.evictCachingConstructor(relation1.getConstructor());
                    cdRelationJpaPort.delete(relation1);
                });
                break;

            default:
                throw new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR);
        }
    }
}
