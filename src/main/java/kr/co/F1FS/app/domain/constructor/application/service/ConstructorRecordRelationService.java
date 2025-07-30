package kr.co.F1FS.app.domain.constructor.application.service;

import kr.co.F1FS.app.domain.constructor.application.mapper.ConstructorRecordRelationMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.in.SinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRecordRelationRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructorRecordRelationService implements ConstructorRecordRelationUseCase {
    private final CurrentSeasonUseCase currentSeasonUseCase;
    private final SinceDebutUseCase sinceDebutUseCase;
    private final ConstructorRecordRelationMapper relationMapper;
    private final ConstructorRecordRelationRepository relationRepository;

    @Override
    public void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut){
        ConstructorRecordRelation relation = relationMapper.toConstructorRecordRelation(constructor, currentSeason,
                sinceDebut);

        relationRepository.save(relation);
    }

    @Override
    @Cacheable(value = "ConstructorStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager")
    public List<ResponseConstructorStandingDTO> getConstructorStandingList(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);
        List<ConstructorRecordRelation> relationList =
                relationRepository.findConstructorRecordRelationsByRacingClassAndEntryClassSeason(racingClass, true);

        return relationList.stream()
                .map(relation -> relationMapper.toResponseConstructorStandingDTO(relation))
                .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
                .toList();
    }

    @Override
    public ConstructorRecordRelation findByConstructor(Constructor constructor){
        return relationRepository.findByConstructorInfo(constructor)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_RECORD_ERROR));
    }

    @Override
    public void updateRecordForRace(ConstructorRecordRelation relation, int position, int points, boolean isFastestLap){
        currentSeasonUseCase.updateCurrentSeasonForRace(relation.getCurrentSeason(), position, points, isFastestLap);
        sinceDebutUseCase.updateSinceDebutForRace(relation.getSinceDebut(), position, isFastestLap);
    }

    @Override
    public void updateRecordForQualifying(ConstructorRecordRelation relation, int position){
        currentSeasonUseCase.updateCurrentSeasonForQualifying(relation.getCurrentSeason(), position);
        sinceDebutUseCase.updateSinceDebutForQualifying(relation.getSinceDebut(), position);
    }
}
