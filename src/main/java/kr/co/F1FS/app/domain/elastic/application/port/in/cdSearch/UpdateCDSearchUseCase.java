package kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;

public interface UpdateCDSearchUseCase {
    void modify(ConstructorDocument document, Constructor constructor);
    void modify(DriverDocument document, Driver driver);
}
