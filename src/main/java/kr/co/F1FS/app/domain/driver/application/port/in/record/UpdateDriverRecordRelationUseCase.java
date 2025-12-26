package kr.co.F1FS.app.domain.driver.application.port.in.record;

import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;

public interface UpdateDriverRecordRelationUseCase {
    void updateRecordForRace(DriverRecordRelation relation, int position, int points, boolean isFastestLap);
    void updateRecordForQualifying(DriverRecordRelation relation, int position);
    void updateChampionshipRank(String racingClassCode);
}
