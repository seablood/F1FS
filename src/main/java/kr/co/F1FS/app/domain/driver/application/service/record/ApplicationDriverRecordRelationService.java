package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.mapper.record.DriverRecordRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.record.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationDriverRecordRelationService implements DriverRecordRelationUseCase {
    private final DriverRecordRelationMapper relationMapper;
    private final DriverRecordRelationJpaPort relationJpaPort;

    @Override
    @Cacheable(value = "DriverStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager")
    public List<ResponseDriverStandingDTO> getDriverStandingList(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<DriverRecordRelation> relationList = relationJpaPort.findDriverRecordRelationsByRacingClassAndEntryClassSeason(
                racingClass, true
        );

        return relationList.stream()
                .map(relation -> relationMapper.toResponseDriverStandingDTO(relation))
                .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
                .toList();
    }
}
