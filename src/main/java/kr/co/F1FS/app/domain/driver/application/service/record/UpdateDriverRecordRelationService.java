package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.port.in.record.UpdateDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.UpdateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateDriverRecordRelationService implements UpdateDriverRecordRelationUseCase {
    private final DriverRecordRelationJpaPort relationJpaPort;
    private final DriverRecordRelationDomainService relationDomainService;
    private final UpdateCurrentSeasonUseCase updateCurrentSeasonUseCase;

    @Override
    public void updateRecordForRace(DriverRecordRelation relation, int position, int points, boolean isFastestLap) {
        if(!relation.isEntryClassSeason()) relationDomainService.updateEntryClassSeason(relation, true);
        relationJpaPort.save(relation);

        updateCurrentSeasonUseCase.updateCurrentSeasonForRace(relation.getCurrentSeason(), position, points, isFastestLap);
    }

    @Override
    public void updateRecordForQualifying(DriverRecordRelation relation, int position) {
        if(!relation.isEntryClassSeason()) relationDomainService.updateEntryClassSeason(relation, true);

        updateCurrentSeasonUseCase.updateCurrentSeasonForQualifying(relation.getCurrentSeason(), position);
    }

    @Override
    public void updateChampionshipRank(String racingClassCode) {
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<DriverRecordRelation> relationList = relationJpaPort.findDriverRecordRelationsByRacingClassAndEntryClassSeason(
                racingClass, true
        ).stream().sorted((o1, o2) -> Integer.compare(o2.getCurrentSeason().getChampionshipPoint(), o1.getCurrentSeason().getChampionshipPoint())).toList();

        int rank = 1;
        for (DriverRecordRelation relation : relationList){
            CurrentSeason record = relation.getCurrentSeason();
            updateCurrentSeasonUseCase.updateChampionshipRank(record, rank++);
        }
    }
}
