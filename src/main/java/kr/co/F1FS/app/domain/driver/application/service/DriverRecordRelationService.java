package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.driver.application.mapper.DriverRecordRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRecordRelationRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverRecordRelationService implements DriverRecordRelationUseCase {
    private final DriverRecordRelationMapper relationMapper;
    private final DriverRecordRelationRepository relationRepository;

    @Override
    public void save(Driver driver, CurrentSeason currentSeason){
        DriverRecordRelation relation = relationMapper.toDriverRecordRelation(driver, currentSeason);

        relationRepository.save(relation);
    }

    @Override
    public List<ResponseDriverStandingDTO> getDriverStandingList(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<DriverRecordRelation> relationList = relationRepository.findDriverRecordRelationsByRacingClassAndEntryClassSeason(
                racingClass, true
        );

        return relationList.stream()
                .map(relation -> relationMapper.toResponseDriverStandingDTO(relation))
                .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
                .toList();
    }

    public DriverRecordRelation getRecordByDriverAndRacingClass(Driver driver){
        return relationRepository.findDriverRecordRelationByDriverInfoAndRacingClass(driver, driver.getRacingClass())
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR));
    }
}
