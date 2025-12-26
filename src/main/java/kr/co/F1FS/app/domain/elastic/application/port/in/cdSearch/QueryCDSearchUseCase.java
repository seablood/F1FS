package kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch;

import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;

public interface QueryCDSearchUseCase {
    ConstructorDocument findConstructorDocumentById(Long id);
    DriverDocument findDriverDocumentById(Long id);
}
