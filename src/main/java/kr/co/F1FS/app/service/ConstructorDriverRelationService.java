package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.model.*;
import kr.co.F1FS.app.repository.*;
import kr.co.F1FS.app.util.CacheEvictUtil;
import kr.co.F1FS.app.util.RacingClass;
import kr.co.F1FS.app.util.constructor.ConstructorException;
import kr.co.F1FS.app.util.constructor.ConstructorExceptionType;
import kr.co.F1FS.app.util.driver.DriverException;
import kr.co.F1FS.app.util.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorDriverRelationService {
    private final DriverRecordRelationService recordRelationService;
    private final DriverDebutRelationService debutRelationService;
    private final ConstructorRepository constructorRepository;
    private final DriverRepository driverRepository;
    private final ConstructorDriverRelationRepository relationRepository;
    private final CurrentSeasonRepository currentSeasonRepository;
    private final SinceDebutRepository sinceDebutRepository;
    private final CacheEvictUtil cacheEvictUtil;

    public void save(Constructor constructor, Driver driver){
        ConstructorDriverRelation relation = ConstructorDriverRelation.builder()
                .constructor(constructor)
                .driver(driver)
                .racingClass(constructor.getRacingClass())
                .build();
        relationRepository.save(relation);
    }

    @Transactional
    public void transfer(Integer number, String constructorName){
        Driver driver = driverRepository.findByNumber(number)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
        Constructor constructor = constructorRepository.findByName(constructorName)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
        cacheEvictUtil.evictCachingDriver(driver);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(relationRepository.existsConstructorDriverRelationByDriverAndRacingClass(driver, constructor.getRacingClass())){
            if(relationRepository.existsConstructorDriverRelationByDriverAndConstructor(driver, constructor)){
                driver.updateTeam(constructor.getName());
                modifyRacingClass(driver, constructor.getRacingClass());

                driverRepository.saveAndFlush(driver);
            } else {
                ConstructorDriverRelation relation = relationRepository.findConstructorDriverRelationByDriverAndRacingClass(
                        driver, constructor.getRacingClass()
                ).orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR));
                relation.updateConstructor(constructor);

                relationRepository.saveAndFlush(relation);
            }
        } else {
            ConstructorDriverRelation relation = ConstructorDriverRelation.builder()
                    .constructor(constructor)
                    .driver(driver)
                    .racingClass(constructor.getRacingClass())
                    .build();
            driver.updateTeam(constructorName);
            modifyRacingClass(driver, constructor.getRacingClass());

            driverRepository.saveAndFlush(driver);
            relationRepository.saveAndFlush(relation);
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "DriverDTO", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverCurrentSeason", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverSinceDebut", key = "#driver.id", cacheManager = "redisLongCacheManager")
    })
    public void modifyRacingClass(Driver driver, RacingClass racingClass){
        driver.updateRacingClass(racingClass);

        if(!debutRelationService.existsRelation(driver, racingClass)){
            CurrentSeason currentSeason = new CurrentSeason();
            SinceDebut sinceDebut = new SinceDebut();

            recordRelationService.save(driver, currentSeason);
            debutRelationService.save(driver, sinceDebut);
            currentSeasonRepository.save(currentSeason);
            sinceDebutRepository.save(sinceDebut);
        }
    }

    @Transactional
    public void delete(Long id, String option) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
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
