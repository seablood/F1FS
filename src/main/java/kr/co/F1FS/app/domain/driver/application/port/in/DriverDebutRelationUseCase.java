package kr.co.F1FS.app.domain.driver.application.port.in;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface DriverDebutRelationUseCase {
    void save(Driver driver, SinceDebut sinceDebut);
}
