package kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;

public interface CreateCDSearchUseCase {
    void save(Constructor constructor);
    void save(Driver driver);
}
