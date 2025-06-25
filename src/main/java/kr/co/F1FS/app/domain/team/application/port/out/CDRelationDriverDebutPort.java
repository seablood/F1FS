package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;

public interface CDRelationDriverDebutPort {
    boolean existsRelation(Driver driver, RacingClass racingClass);
    void save(Driver driver, SinceDebut sinceDebut);
}
