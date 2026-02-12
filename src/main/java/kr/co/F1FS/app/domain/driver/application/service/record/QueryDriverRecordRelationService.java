package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.mapper.record.DriverRecordRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.record.QueryDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryDriverRecordRelationService implements QueryDriverRecordRelationUseCase {
    private final DriverRecordRelationJpaPort relationJpaPort;
    private final DriverRecordRelationMapper relationMapper;

    @Override
    public DriverRecordRelation findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver) {
        return relationJpaPort.findDriverRecordRelationByDriverInfoAndRacingClass(driver, driver.getRacingClass());
    }

    @Override
    public List<ResponseDriverStandingDTO> findDriverRecordRelationsByRacingClassAndEntryClassSeasonForDTO(RacingClass racingClass) {
        List<DriverRecordRelation> relationList = relationJpaPort.findDriverRecordRelationsByRacingClassAndEntryClassSeason(
                racingClass, true
        );

        return relationList.stream()
                .map(relation -> relationMapper.toResponseDriverStandingDTO(relation))
                .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
                .toList();
    }
}
