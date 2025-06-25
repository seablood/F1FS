package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.application.service.DriverRecordRelationService;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationDriverRecordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CDRelationDriverRecordAdapter implements CDRelationDriverRecordPort {
    private final DriverRecordRelationService recordRelationService;

    @Override
    public void save(Driver driver, CurrentSeason currentSeason) {
        recordRelationService.save(driver, currentSeason);
    }
}
