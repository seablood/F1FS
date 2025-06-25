package kr.co.F1FS.app.domain.admin.constructor.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;

public interface AdminConstructorCDSearchPort {
    void save(ConstructorDocument constructorDocument);
}
