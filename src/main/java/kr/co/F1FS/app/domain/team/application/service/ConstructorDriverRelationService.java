package kr.co.F1FS.app.domain.team.application.service;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.team.application.mapper.ConstructorDriverRelationMapper;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.*;
import kr.co.F1FS.app.domain.team.infrastructure.repository.ConstructorDriverRelationRepository;
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
    private final CDRelationDriverDebutPort driverDebutPort;
    private final CDRelationConstructorPort constructorPort;
    private final CDRelationDriverPort driverPort;
    private final ConstructorDriverRelationRepository relationRepository;
    private final CDRelationRecordPort recordPort;
    private final ConstructorDriverRelationMapper relationMapper;
    private final CacheEvictUtil cacheEvictUtil;

    public ConstructorDriverRelation save(Constructor constructor, Driver driver){
        ConstructorDriverRelation relation = relationMapper.toConstructorDriverRelation(constructor, driver);
        return relation;
    }

    @Transactional
    public void transfer(Integer number, String constructorName){
        Driver driver = driverPort.findByNumber(number);
        Constructor constructor = constructorPort.findByName(constructorName);
        cacheEvictUtil.evictCachingDriver(driver);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(relationRepository.existsConstructorDriverRelationByDriverAndRacingClass(driver, constructor.getRacingClass())){
            if(relationRepository.existsConstructorDriverRelationByDriverAndConstructor(driver, constructor)){
                driverUseCase.updateTeam(driver, constructor.getName(), constructor.getEngName());
                modifyRacingClass(driver, constructor.getRacingClass());
            } else {
                ConstructorDriverRelation relation = relationRepository.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, constructor.getRacingClass()
                ).orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR));
                relation.updateConstructor(constructor);
                driverUseCase.updateTeam(driver, constructor.getName(), constructor.getEngName());
                modifyRacingClass(driver, constructor.getRacingClass());

                relationRepository.saveAndFlush(relation);
            }
        } else {
            ConstructorDriverRelation relation = ConstructorDriverRelation.builder()
                    .constructor(constructor)
                    .driver(driver)
                    .racingClass(constructor.getRacingClass())
                    .build();
            driverUseCase.updateTeam(driver, constructor.getName(), constructor.getEngName());
            modifyRacingClass(driver, constructor.getRacingClass());

            driverPort.saveAndFlush(driver);
            relationRepository.saveAndFlush(relation);
        }
    }

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

        if(!driverDebutPort.existsRelation(driver, racingClass)){
            CurrentSeason currentSeason = new CurrentSeason();
            SinceDebut sinceDebut = new SinceDebut();

            driverRecordUseCase.save(driver, currentSeason);
            driverDebutUseCase.save(driver, sinceDebut);
            recordPort.save(currentSeason, sinceDebut);
        }
    }

    @Transactional
    public void delete(Long id, String option) {
        Driver driver = driverPort.findById(id);
        cacheEvictUtil.evictCachingDriver(driver);

        option(driver, option);
    }

    public void option(Driver driver, String option){
        ConstructorDriverRelation relation;

        switch (option){
            case "currentTeam" :
                relation = relationRepository.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, driver.getRacingClass()
                ).orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR));
                cacheEvictUtil.evictCachingConstructor(relation.getConstructor());
                relationRepository.delete(relation);
                break;

            case "F1", "F2", "F3", "F1_ACADEMY", "RESERVE" :
                RacingClass racingClass = RacingClass.valueOf(option);
                relation = relationRepository.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, racingClass
                ).orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR));
                cacheEvictUtil.evictCachingConstructor(relation.getConstructor());
                relationRepository.delete(relation);
                break;

            case "all" :
                List<ConstructorDriverRelation> list = relationRepository.findConstructorDriverRelationByDriver(driver);
                list.stream().forEach(relation1 -> {
                    cacheEvictUtil.evictCachingConstructor(relation1.getConstructor());
                    relationRepository.delete(relation1);
                });
                break;

            default:
                throw new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR);
        }
    }
}
