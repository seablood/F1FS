package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;

public interface CDSearchRepoPort {
    DriverDocument save(DriverDocument document);
    ConstructorDocument save(ConstructorDocument document);
    ConstructorDocument findConstructorDocumentById(Long id);
    DriverDocument findDriverDocumentById(Long id);
}
