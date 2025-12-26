package kr.co.F1FS.app.domain.constructor.application.mapper.record;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import org.springframework.stereotype.Component;

@Component
public class ConstructorRecordRelationMapper {
    public ConstructorRecordRelation toConstructorRecordRelation(Constructor constructor, CurrentSeason currentSeason,
                                                                 SinceDebut sinceDebut){
        return ConstructorRecordRelation.builder()
                .constructor(constructor)
                .currentSeason(currentSeason)
                .sinceDebut(sinceDebut)
                .racingClass(constructor.getRacingClass())
                .build();
    }

    public ResponseConstructorStandingDTO toResponseConstructorStandingDTO(ConstructorRecordRelation relation){
        return ResponseConstructorStandingDTO.builder()
                .constructorName(relation.getConstructorInfo().getName())
                .points(relation.getCurrentSeason().getChampionshipPoint())
                .build();
    }
}
