package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.mapper.DriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.presentation.dto.CreateDriverDTO;
import kr.co.F1FS.app.domain.driver.presentation.dto.ModifyDriverCommand;
import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.SinceDebutUseCase;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationDriverUseCase;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import kr.co.F1FS.app.global.application.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverService implements DriverUseCase {
    private final ConstructorDriverUseCase constructorUseCase;
    private final ConstructorDriverRelationDriverUseCase cdRelationUseCase;
    private final CurrentSeasonUseCase currentSeasonUseCase;
    private final SinceDebutUseCase sinceDebutUseCase;
    private final DriverMapper driverMapper;
    private final DriverJpaPort driverJpaPort;
    private final DriverRecordRelationUseCase recordRelationUseCase;
    private final DriverDebutRelationUseCase debutRelationUseCase;
    private final ValidationService validationService;

    @Override
    @Transactional
    public Driver save(CreateDriverDTO driverDTO, CreateCurrentSeasonDTO currentSeasonDTO,
                       CreateSinceDebutDTO sinceDebutDTO){
        Driver driver = driverMapper.toDriver(driverDTO);
        validationService.checkValid(driver);
        CurrentSeason currentSeason = currentSeasonUseCase.toCurrentSeason(currentSeasonDTO);
        SinceDebut sinceDebut = sinceDebutUseCase.toSinceDebut(sinceDebutDTO);
        if(!driver.getTeam().equals("FA")){
            Constructor constructor = constructorUseCase.findByNameNotDTONotCache(driver.getTeam());
            driver.updateEngTeam(constructor.getEngName());

            cdRelationUseCase.save(constructor, driver);
        }
        recordRelationUseCase.save(driver, currentSeason);
        debutRelationUseCase.save(driver, sinceDebut);

        currentSeasonUseCase.save(currentSeason);
        sinceDebutUseCase.save(sinceDebut);

        return driverJpaPort.save(driver);
    }

    @Override
    public Driver save(Driver driver) {
        return driverJpaPort.save(driver);
    }

    @Override
    public Driver saveAndFlush(Driver driver) {
        return driverJpaPort.saveAndFlush(driver);
    }

    @Override
    public Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return driverJpaPort.findAll(pageable);
    }

    @Override
    @Cacheable(value = "DriverDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseDriverDTO findById(Long id){
        Driver driver = findByIdNotDTO(id);
        ResponseCurrentSeasonDTO currentSeasonDTO = getCurrentSeason(driver);
        ResponseSinceDebutDTO sinceDebutDTO = getSinceDebut(driver);

        return driverMapper.toResponseDriverDTO(driver, currentSeasonDTO, sinceDebutDTO);
    }

    @Override
    @Cacheable(value = "DriverDTOByEngName", key = "#engName", cacheManager = "redisLongCacheManager")
    public ResponseDriverDTO findByEngName(String engName){
        Driver driver = driverJpaPort.findByEngName(engName);
        ResponseCurrentSeasonDTO currentSeasonDTO = getCurrentSeason(driver);
        ResponseSinceDebutDTO sinceDebutDTO = getSinceDebut(driver);

        return driverMapper.toResponseDriverDTO(driver, currentSeasonDTO, sinceDebutDTO);
    }

    @Override
    @Cacheable(value = "Driver", key = "#id", cacheManager = "redisLongCacheManager")
    public Driver findByIdNotDTO(Long id){
        return driverJpaPort.findById(id);
    }

    @Override
    public Driver findByIdNotDTONotCache(Long id) {
        return driverJpaPort.findById(id);
    }

    @Override
    public Driver findByNameNotDTONotCache(String name) {
        return driverJpaPort.findByName(name);
    }

    @Override
    public Driver findByNumberNotDTONotCache(Integer number) {
        return driverJpaPort.findByNumber(number);
    }

    @Override
    public void modify(Driver driver, ModifyDriverCommand command){
        driver.modify(command);
    }

    @Override
    public void updateTeam(Driver driver, String constructorName, String constructorEngName){
        driver.updateTeam(constructorName, constructorEngName);
        driverJpaPort.saveAndFlush(driver);
    }

    @Override
    public void updateRacingClass(Driver driver, RacingClass racingClass) {
        driver.updateRacingClass(racingClass);
        driverJpaPort.saveAndFlush(driver);
    }

    @Override
    public void updateRecordForRace(Driver driver, int position, int points, boolean isFastestLap){
        DriverRecordRelation recordRelation = recordRelationUseCase.getRecordByDriverAndRacingClass(driver);
        DriverDebutRelation debutRelation = debutRelationUseCase.getSinceDebutByDriverAndRacingClass(driver);

        if(!recordRelation.isEntryClassSeason()) recordRelation.updateEntryClassSeason(true);

        currentSeasonUseCase.updateCurrentSeasonForRace(recordRelation.getCurrentSeason(), position, points, isFastestLap);
        sinceDebutUseCase.updateSinceDebutForRace(debutRelation.getSinceDebut(), position, isFastestLap);
    }

    @Override
    public void updateRecordForQualifying(Driver driver, int position){
        DriverRecordRelation recordRelation = recordRelationUseCase.getRecordByDriverAndRacingClass(driver);
        DriverDebutRelation debutRelation = debutRelationUseCase.getSinceDebutByDriverAndRacingClass(driver);

        if(!recordRelation.isEntryClassSeason()) recordRelation.updateEntryClassSeason(true);

        currentSeasonUseCase.updateCurrentSeasonForQualifying(recordRelation.getCurrentSeason(), position);
        sinceDebutUseCase.updateSinceDebutForQualifying(debutRelation.getSinceDebut(), position);
    }

    @Override
    public void increaseFollower(Driver driver){
        driver.increaseFollower();
    }

    @Override
    public void decreaseFollower(Driver driver){
        driver.decreaseFollower();
    }

    @Override
    @Cacheable(value = "DriverCurrentSeason", key = "#driver.id", cacheManager = "redisLongCacheManager")
    public ResponseCurrentSeasonDTO getCurrentSeason(Driver driver){
        ResponseCurrentSeasonDTO currentSeasonDTO = currentSeasonUseCase.toResponseCurrentSeasonDTO(
                recordRelationUseCase.getRecordByDriverAndRacingClass(driver).getCurrentSeason()
        );

        return currentSeasonDTO;
    }

    @Override
    @Cacheable(value = "DriverSinceDebut", key = "#driver.id", cacheManager = "redisLongCacheManager")
    public ResponseSinceDebutDTO getSinceDebut(Driver driver) {
        ResponseSinceDebutDTO sinceDebutDTO = sinceDebutUseCase.toResponseSinceDebutDTO(
                debutRelationUseCase.getSinceDebutByDriverAndRacingClass(driver).getSinceDebut()
        );

        return sinceDebutDTO;
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            default:
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }
}
