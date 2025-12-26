package kr.co.F1FS.app.domain.constructor.application.service;

import kr.co.F1FS.app.domain.constructor.application.mapper.record.ConstructorRecordRelationMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.UpdateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.UpdateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorRecordRelationService implements ConstructorRecordRelationUseCase {
    private final UpdateCurrentSeasonUseCase updateCurrentSeasonUseCase;
    private final UpdateSinceDebutUseCase updateSinceDebutUseCase;
    private final ConstructorRecordRelationMapper relationMapper;
    private final ConstructorRecordRelationJpaPort relationJpaPort;

    @Override
    public void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut){
        ConstructorRecordRelation relation = relationMapper.toConstructorRecordRelation(constructor, currentSeason,
                sinceDebut);

        relationJpaPort.save(relation);
    }

    @Override
    @Cacheable(value = "ConstructorStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager")
    public List<ResponseConstructorStandingDTO> getConstructorStandingList(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<ConstructorRecordRelation> relationList =
                relationJpaPort.findConstructorRecordRelationsByRacingClassAndEntryClassSeason(racingClass, true);

        return relationList.stream()
                .map(relation -> relationMapper.toResponseConstructorStandingDTO(relation))
                .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
                .toList();
    }

    @Override
    public ConstructorRecordRelation findByConstructor(Constructor constructor){
        return relationJpaPort.findByConstructorInfo(constructor);
    }

    @Override
    public void updateRecordForRace(ConstructorRecordRelation relation, int position, int points, boolean isFastestLap){
        updateCurrentSeasonUseCase.updateCurrentSeasonForRace(relation.getCurrentSeason(), position, points, isFastestLap);
        updateSinceDebutUseCase.updateSinceDebutForRace(relation.getSinceDebut(), position, isFastestLap);
    }

    @Override
    public void updateRecordForQualifying(ConstructorRecordRelation relation, int position){
        updateCurrentSeasonUseCase.updateCurrentSeasonForQualifying(relation.getCurrentSeason(), position);
        updateSinceDebutUseCase.updateSinceDebutForQualifying(relation.getSinceDebut(), position);
    }

    @Override
    public void updateChampionshipRank(String racingClassCode){
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
