package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.port.in.record.UpdateConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.UpdateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.UpdateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateConstructorRecordRelationService implements UpdateConstructorRecordRelationUseCase {
    private final ConstructorRecordRelationJpaPort relationJpaPort;
    private final ConstructorRecordRelationDomainService relationDomainService;
    private final UpdateCurrentSeasonUseCase updateCurrentSeasonUseCase;
    private final UpdateSinceDebutUseCase updateSinceDebutUseCase;

    @Override
    public void updateRecordForRace(ConstructorRecordRelation relation, int position, int points, boolean isFastestLap) {
        if(!relation.isEntryClassSeason()) relationDomainService.updateEntryClassSeason(relation, true);
        relationJpaPort.save(relation);

        updateCurrentSeasonUseCase.updateCurrentSeasonForRace(relation.getCurrentSeason(), position, points, isFastestLap);
        updateSinceDebutUseCase.updateSinceDebutForRace(relation.getSinceDebut(), position, isFastestLap);
    }

    @Override
    public void updateRecordForQualifying(ConstructorRecordRelation relation, int position) {
        if(!relation.isEntryClassSeason()) relationDomainService.updateEntryClassSeason(relation, true);
        relationJpaPort.save(relation);

        updateCurrentSeasonUseCase.updateCurrentSeasonForQualifying(relation.getCurrentSeason(), position);
        updateSinceDebutUseCase.updateSinceDebutForQualifying(relation.getSinceDebut(), position);
    }

    @Override
    public void updateChampionshipRank(String racingClassCode) {
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<ConstructorRecordRelation> relationList = relationJpaPort.findConstructorRecordRelationsByRacingClassAndEntryClassSeason(
                        racingClass, true
                ).stream().sorted((o1, o2) -> Integer.compare(o2.getCurrentSeason().getChampionshipPoint(), o1.getCurrentSeason().getChampionshipPoint()))
                .toList();

        int rank = 1;
        for (ConstructorRecordRelation relation : relationList){
            CurrentSeason record = relation.getCurrentSeason();
            updateCurrentSeasonUseCase.updateChampionshipRank(record, rank++);
        }
    }
}
