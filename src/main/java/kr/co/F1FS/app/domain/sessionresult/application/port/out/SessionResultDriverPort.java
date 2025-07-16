package kr.co.F1FS.app.domain.sessionresult.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;

public interface SessionResultDriverPort {
    Driver getDriverByNameNotDTO(String name);
}
