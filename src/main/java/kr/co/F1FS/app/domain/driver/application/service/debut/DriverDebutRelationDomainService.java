package kr.co.F1FS.app.domain.driver.application.service.debut;

import kr.co.F1FS.app.domain.driver.application.mapper.debut.DriverDebutRelationMapper;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDebutRelationDomainService {
    private final DriverDebutRelationMapper relationMapper;

    public DriverDebutRelation createEntity(Driver driver, SinceDebut sinceDebut){
        return relationMapper.toDriverDebutRelation(driver, sinceDebut);
    }
}
