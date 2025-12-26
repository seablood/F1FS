package kr.co.F1FS.app.domain.elastic.application.port.in.post;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;

public interface QueryPostSearchUseCase {
    PostDocument findById(Long id);
}
