package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.TagDocument;

public interface TagSearchRepoPort {
    void save(TagDocument tagDocument);
}
