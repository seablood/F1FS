package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.driver.application.mapper.DriverRecordRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;
import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverRecordRelationService implements DriverRecordRelationUseCase {
    private final CurrentSeasonUseCase currentSeasonUseCase;
    private final DriverRecordRelationMapper relationMapper;
    private final DriverRecordRelationJpaPort relationJpaPort;

    @Override
    public void save(Driver driver, CurrentSeason currentSeason){
        DriverRecordRelation relation = relationMapper.toDriverRecordRelation(driver, currentSeason);

        relationJpaPort.save(relation);
    }

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

    @Override
    public void updateChampionshipRank(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<DriverRecordRelation> relationList = relationJpaPort.findDriverRecordRelationsByRacingClassAndEntryClassSeason(
                racingClass, true
        ).stream().sorted((o1, o2) -> Integer.compare(o2.getCurrentSeason().getChampionshipPoint(), o1.getCurrentSeason().getChampionshipPoint())).toList();

        int rank = 1;
        for (DriverRecordRelation relation : relationList){
            CurrentSeason record = relation.getCurrentSeason();
            currentSeasonUseCase.updateChampionshipRank(record, rank++);
            currentSeasonUseCase.saveAndFlush(record);
        }
    }

    @Override
    public DriverRecordRelation getRecordByDriverAndRacingClass(Driver driver){
        return relationJpaPort.findDriverRecordRelationByDriverInfoAndRacingClass(driver, driver.getRacingClass());
    }
}
