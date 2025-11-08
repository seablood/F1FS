package kr.co.F1FS.app.domain.admin.driver.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;

public interface AdminDriverCDSearchPort {
    void save(DriverDocument driverDocument);
    DriverDocument getDriverDocument(Long id);
}
