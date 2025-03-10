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
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseDriverDTO findById(Long id){
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
        ResponseCurrentSeasonDTO currentSeasonDTO = getCurrentSeason(driver);
        ResponseSinceDebutDTO sinceDebutDTO = getSinceDebut(driver);

        return ResponseDriverDTO.toDto(driver, currentSeasonDTO, sinceDebutDTO);
    }

    public List<ResponseSimpleDriverDTO> findByRacingClass(String findClass){
        RacingClass racingClass= RacingClass.valueOf(findClass);
        List<ResponseSimpleDriverDTO> driverDTOList = driverRepository.findAllByRacingClass(racingClass).stream()
                .map(driver -> ResponseSimpleDriverDTO.toDto(driver))
                .toList();

        return driverDTOList;
    }

    public List<ResponseSimpleDriverDTO> findByNameList(String search){
        List<ResponseSimpleDriverDTO> drivers = driverRepository
                .findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(search, search).stream()
                .map(driver -> ResponseSimpleDriverDTO.toDto(driver))
                .toList();

        return drivers;
    }

    public Driver findByName(String name){
        return driverRepository.findByName(name)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    @Transactional
    public void modifyRacingClass(Long id, String modifyClass){
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
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

    public ResponseCurrentSeasonDTO getCurrentSeason(Driver driver){
        ResponseCurrentSeasonDTO currentSeasonDTO = ResponseCurrentSeasonDTO.toDto(driver.getRecords()
                .stream().filter(recordRelation -> recordRelation.getRacingClass() == driver.getRacingClass())
                .findFirst().orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR))
                .getCurrentSeason());

        return currentSeasonDTO;
    }

    public ResponseSinceDebutDTO getSinceDebut(Driver driver){
        ResponseSinceDebutDTO sinceDebutDTO = ResponseSinceDebutDTO.toDto(driver.getDebuts().stream()
                .filter(debutRelation -> debutRelation.getRacingClass() == driver.getRacingClass()).findFirst()
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_DEBUT_ERROR)).getSinceDebut());

        return sinceDebutDTO;
    }
}
