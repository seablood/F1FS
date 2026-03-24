package kr.co.F1FS.app.domain.driver.infrastructure.adapter.record;

import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRecordRelationRepository;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.dsl.DriverRecordRelationDSLRepository;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
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
    private final DriverRecordRelationDSLRepository relationDSLRepository;

    @Override
    public DriverRecordRelation save(DriverRecordRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public List<ResponseDriverStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass) {
        return relationDSLRepository.findAllByRacingClassAndEntryClassSeason(racingClass);
    }

    @Override
    public List<DriverRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass) {
        return relationDSLRepository.findAllByRacingClassAndEntryClassSeasonNotDTO(racingClass);
    }

    @Override
    public DriverRecordRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass) {
        return relationDSLRepository.findByDriverAndRacingClass(driverId, racingClass)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_RECORD_ERROR));
    }
}
