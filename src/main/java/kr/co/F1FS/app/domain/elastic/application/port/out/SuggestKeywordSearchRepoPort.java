package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.SuggestKeywordDocument;

import java.util.List;

public interface SuggestKeywordSearchRepoPort {
    void save(SuggestKeywordDocument document);
    void saveAll(List<SuggestKeywordDocument> list);
}
