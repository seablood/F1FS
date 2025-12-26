package kr.co.F1FS.app.domain.constructor.application.port.in.record;

import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;

public interface UpdateConstructorRecordRelationUseCase {
    void updateRecordForRace(ConstructorRecordRelation relation, int position, int points, boolean isFastestLap);
    void updateRecordForQualifying(ConstructorRecordRelation relation, int position);
    void updateChampionshipRank(String racingClassCode);
}
