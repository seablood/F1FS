package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixSuggestDocument;

public interface GrandPrixSuggestSearchRepoPort {
    void save(GrandPrixSuggestDocument document);
}
