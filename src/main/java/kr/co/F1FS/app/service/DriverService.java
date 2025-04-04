package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.*;
import kr.co.F1FS.app.model.*;
import kr.co.F1FS.app.repository.*;
import kr.co.F1FS.app.util.RacingClass;
import kr.co.F1FS.app.util.ValidationService;
import kr.co.F1FS.app.util.constructor.ConstructorException;
import kr.co.F1FS.app.util.constructor.ConstructorExceptionType;
import kr.co.F1FS.app.util.driver.DriverException;
import kr.co.F1FS.app.util.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final ConstructorRepository constructorRepository;
    private final ConstructorDriverRelationService relationService;
    private final DriverRecordRelationService recordRelationService;
    private final DriverDebutRelationService debutRelationService;
    private final CurrentSeasonRepository currentSeasonRepository;
    private final SinceDebutRepository sinceDebutRepository;
    private final ValidationService validationService;

    @Transactional
    public Driver save(CombinedDriverRequest request){
        Driver driver = CreateDriverDTO.toEntity(request.getDriverDTO());
        validationService.checkValid(driver);
        CurrentSeason currentSeason = CreateCurrentSeasonDTO.toEntity(request.getCurrentSeasonDTO());
        SinceDebut sinceDebut = CreateSinceDebutDTO.toEntity(request.getSinceDebutDTO());
        if(!driver.getTeam().equals("FA")){
            Constructor constructor = constructorRepository.findByName(driver.getTeam())
                    .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));

            relationService.save(constructor, driver);
        }
        recordRelationService.save(driver, currentSeason);
        debutRelationService.save(driver, sinceDebut);

        currentSeasonRepository.save(currentSeason);
        sinceDebutRepository.save(sinceDebut);
        return driverRepository.save(driver);
    }

    @Cacheable(value = "DriverDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseDriverDTO findById(Long id){
        Driver driver = findByIdNotDTO(id);
        ResponseCurrentSeasonDTO currentSeasonDTO = getCurrentSeason(driver);
        ResponseSinceDebutDTO sinceDebutDTO = getSinceDebut(driver);

        return ResponseDriverDTO.toDto(driver, currentSeasonDTO, sinceDebutDTO);
    }

    @Cacheable(value = "Driver", key = "#id", cacheManager = "redisLongCacheManager")
    public Driver findByIdNotDTO(Long id){
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    public Page<ResponseSimpleDriverDTO> findByRacingClass(String findClass, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        RacingClass racingClass= RacingClass.valueOf(findClass);

        return driverRepository.findAllByRacingClass(racingClass, pageable)
                .map(driver -> ResponseSimpleDriverDTO.toDto(driver));
    }

    public Page<ResponseSimpleDriverDTO> findByNameList(String search, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));

        return driverRepository.findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(search, search, pageable)
                .map(driver -> ResponseSimpleDriverDTO.toDto(driver));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "DriverDTO", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverCurrentSeason", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverSinceDebut", key = "#driver.id", cacheManager = "redisLongCacheManager")
    })
    public void modifyRacingClass(Driver driver, String modifyClass){
        RacingClass racingClass = RacingClass.valueOf(modifyClass);

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

    @Cacheable(value = "DriverCurrentSeason", key = "#driver.id", cacheManager = "redisLongCacheManager")
    public ResponseCurrentSeasonDTO getCurrentSeason(Driver driver){
        ResponseCurrentSeasonDTO currentSeasonDTO = ResponseCurrentSeasonDTO.toDto(driver.getRecords()
                .stream().filter(recordRelation -> recordRelation.getRacingClass() == driver.getRacingClass())
                .findFirst().orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR))
                .getCurrentSeason());

        return currentSeasonDTO;
    }

    @Cacheable(value = "DriverSinceDebut", key = "#driver.id", cacheManager = "redisLongCacheManager")
    public ResponseSinceDebutDTO getSinceDebut(Driver driver) {
        ResponseSinceDebutDTO sinceDebutDTO = ResponseSinceDebutDTO.toDto(driver.getDebuts().stream()
                .filter(debutRelation -> debutRelation.getRacingClass() == driver.getRacingClass()).findFirst()
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_DEBUT_ERROR)).getSinceDebut());

        return sinceDebutDTO;
    }
}
