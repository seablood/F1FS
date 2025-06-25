package kr.co.F1FS.app.domain.driver.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.driver.application.mapper.DriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverConstructorPort;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverRecordPort;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverTeamPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.presentation.dto.CreateDriverDTO;
import kr.co.F1FS.app.domain.team.application.mapper.ConstructorDriverRelationMapper;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService implements DriverUseCase {
    private final DriverConstructorPort driverConstructorPort;
    private final DriverTeamPort driverTeamPort;
    private final DriverRecordPort driverRecordPort;
    private final RecordMapper recordMapper;
    private final DriverMapper driverMapper;
    private final ConstructorDriverRelationMapper relationMapper;
    private final DriverRepository driverRepository;
    private final DriverRecordRelationService recordRelationService;
    private final DriverDebutRelationService debutRelationService;
    private final ValidationService validationService;

    @Transactional
    public Driver save(CreateDriverDTO driverDTO, CreateCurrentSeasonDTO currentSeasonDTO,
                       CreateSinceDebutDTO sinceDebutDTO){
        Driver driver = driverMapper.toDriver(driverDTO);
        validationService.checkValid(driver);
        CurrentSeason currentSeason = recordMapper.toCurrentSeason(currentSeasonDTO);
        SinceDebut sinceDebut = recordMapper.toSinceDebut(sinceDebutDTO);
        if(!driver.getTeam().equals("FA")){
            Constructor constructor = driverConstructorPort.findByName(driver.getTeam());
            driver.updateEngTeam(constructor.getEngName());

            driverTeamPort.save(relationMapper.toConstructorDriverRelation(constructor, driver));
        }
        recordRelationService.save(driver, currentSeason);
        debutRelationService.save(driver, sinceDebut);

        driverRecordPort.save(currentSeason, sinceDebut);

        return driver;
    }

    public Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return driverRepository.findAll(pageable).map(driver -> SimpleResponseDriverDTO.toDto(driver));
    }

    @Cacheable(value = "DriverDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseDriverDTO findById(Long id){
        Driver driver = findByIdNotDTO(id);
        ResponseCurrentSeasonDTO currentSeasonDTO = getCurrentSeason(driver);
        ResponseSinceDebutDTO sinceDebutDTO = getSinceDebut(driver);

        return driverMapper.toResponseDriverDTO(driver, currentSeasonDTO, sinceDebutDTO);
    }

    @Cacheable(value = "Driver", key = "#id", cacheManager = "redisLongCacheManager")
    public Driver findByIdNotDTO(Long id){
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    public void updateTeam(Driver driver, String constructorName, String constructorEngName){
        driver.updateTeam(constructorName, constructorEngName);
        driverRepository.saveAndFlush(driver);
    }

    @Override
    @Transactional
    public void updateRacingClass(Driver driver, RacingClass racingClass) {
        driver.updateRacingClass(racingClass);
        driverRepository.saveAndFlush(driver);
    }

    @Cacheable(value = "DriverCurrentSeason", key = "#driver.id", cacheManager = "redisLongCacheManager")
    public ResponseCurrentSeasonDTO getCurrentSeason(Driver driver){
        ResponseCurrentSeasonDTO currentSeasonDTO = recordMapper.toResponseCurrentSeasonDTO(driver.getRecords()
                .stream().filter(recordRelation -> recordRelation.getRacingClass() == driver.getRacingClass())
                .findFirst().orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR))
                .getCurrentSeason());

        return currentSeasonDTO;
    }

    @Cacheable(value = "DriverSinceDebut", key = "#driver.id", cacheManager = "redisLongCacheManager")
    public ResponseSinceDebutDTO getSinceDebut(Driver driver) {
        ResponseSinceDebutDTO sinceDebutDTO = recordMapper.toResponseSinceDebutDTO(driver.getDebuts().stream()
                .filter(debutRelation -> debutRelation.getRacingClass() == driver.getRacingClass()).findFirst()
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_DEBUT_ERROR)).getSinceDebut());

        return sinceDebutDTO;
    }

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
