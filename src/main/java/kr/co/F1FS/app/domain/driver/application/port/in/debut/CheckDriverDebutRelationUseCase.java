package kr.co.F1FS.app.domain.driver.application.port.in.debut;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;

public interface CheckDriverDebutRelationUseCase {
    boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass);
}
