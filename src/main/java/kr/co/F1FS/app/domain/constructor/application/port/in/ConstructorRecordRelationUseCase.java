package kr.co.F1FS.app.domain.constructor.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

import java.util.List;

public interface ConstructorRecordRelationUseCase {
    void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut);
    List<ResponseConstructorStandingDTO> getConstructorStandingList(String racingClassCode);
    ConstructorRecordRelation findByConstructor(Constructor constructor);
    void updateRecordForRace(ConstructorRecordRelation relation, int position, int points, boolean isFastestLap);
    void updateRecordForQualifying(ConstructorRecordRelation relation, int position);
    void updateChampionshipRank(String racingClassCode);
}
