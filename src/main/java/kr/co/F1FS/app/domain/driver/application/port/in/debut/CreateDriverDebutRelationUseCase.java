package kr.co.F1FS.app.domain.driver.application.port.in.debut;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface CreateDriverDebutRelationUseCase {
    void save(Driver driver, SinceDebut sinceDebut);
}
