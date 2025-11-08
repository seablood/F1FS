package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;

public interface GrandPrixSearchRepoPort {
    GrandPrixDocument save(GrandPrixDocument document);
    GrandPrixDocument findById(Long id);
}
