package kr.co.F1FS.app.domain.driver.infrastructure.adapter.record;

import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRecordRelationRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DriverRecordRelationJpaAdapter implements DriverRecordRelationJpaPort {
    private final DriverRecordRelationRepository relationRepository;

    @Override
    public DriverRecordRelation save(DriverRecordRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public List<DriverRecordRelation> findDriverRecordRelationsByRacingClassAndEntryClassSeason(RacingClass racingClass, boolean entryClassSeason) {
        return relationRepository.findDriverRecordRelationsByRacingClassAndEntryClassSeason(racingClass, entryClassSeason);
    }

    @Override
    public DriverRecordRelation findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationRepository.findDriverRecordRelationByDriverInfoAndRacingClass(driver, racingClass)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR));
    }
}
