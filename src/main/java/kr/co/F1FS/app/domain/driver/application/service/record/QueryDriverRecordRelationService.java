package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.port.in.record.QueryDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryDriverRecordRelationService implements QueryDriverRecordRelationUseCase {
    private final DriverRecordRelationJpaPort relationJpaPort;

    @Override
    public DriverRecordRelation findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver) {
        return relationJpaPort.findDriverRecordRelationByDriverInfoAndRacingClass(driver, driver.getRacingClass());
    }
}
