package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;

public interface DriverCDSearchPort {
    void save(Driver driver);
}
