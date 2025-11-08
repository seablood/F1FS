package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;

public interface PostSearchRepoPort {
    void save(PostDocument document);
    PostDocument findById(Long id);
    void delete(PostDocument document);
}
