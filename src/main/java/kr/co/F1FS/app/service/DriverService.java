package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.*;
import kr.co.F1FS.app.model.*;
import kr.co.F1FS.app.repository.*;
import kr.co.F1FS.app.util.RacingClass;
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

    @Transactional
    public Driver save(CombinedDriverRequest request){
        Driver driver = CreateDriverDTO.toEntity(request.getDriverDTO());
        CurrentSeason currentSeason = CreateCurrentSeasonDTO.toEntity(request.getCurrentSeasonDTO());
        SinceDebut sinceDebut = CreateSinceDebutDTO.toEntity(request.getSinceDebutDTO());
        Constructor constructor = constructorRepository.findByName(driver.getTeam())
                .orElseThrow(() -> new IllegalArgumentException("컨스트럭터를 찾지 못했습니다."));

        relationService.save(constructor, driver);
        recordRelationService.save(driver, currentSeason);
        debutRelationService.save(driver, sinceDebut);

        currentSeasonRepository.save(currentSeason);
        sinceDebutRepository.save(sinceDebut);

        return driverRepository.save(driver);
    }

    public Driver findById(Long id){
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("드라이버를 찾지 못했습니다."));

        return driver;
    }

    public List<ResponseDriverDTO> findByRacingClass(String findClass){
        RacingClass racingClass= RacingClass.valueOf(findClass);
        List<ResponseDriverDTO> driverDTOList = driverRepository.findAllByRacingClass(racingClass).stream()
                .map(driver -> ResponseDriverDTO.toDto(driver))
                .toList();

        return driverDTOList;
    }
}
