package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.mapper.record.DriverRecordRelationMapper;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRecordRelationDomainService {
    private final DriverRecordRelationMapper relationMapper;

    public DriverRecordRelation createEntity(Driver driver, CurrentSeason currentSeason){
        return relationMapper.toDriverRecordRelation(driver, currentSeason);
    }

    public void updateEntryClassSeason(DriverRecordRelation recordRelation, boolean entryClassSeason){
        recordRelation.updateEntryClassSeason(entryClassSeason);
    }
}
