package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.driver.application.mapper.DriverRecordRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRecordRelationRepository;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRecordRelationService implements DriverRecordRelationUseCase {
    private final DriverRecordRelationMapper relationMapper;
    private final DriverRecordRelationRepository relationRepository;

    public void save(Driver driver, CurrentSeason currentSeason){
        DriverRecordRelation relation = relationMapper.toDriverRecordRelation(driver, currentSeason);

        relationRepository.save(relation);
    }

    public DriverRecordRelation getRecordByDriverAndRacingClass(Driver driver){
        return relationRepository.findDriverRecordRelationByDriverInfoAndRacingClass(driver, driver.getRacingClass())
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR));
    }
}
