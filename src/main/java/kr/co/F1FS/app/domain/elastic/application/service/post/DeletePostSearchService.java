package kr.co.F1FS.app.domain.elastic.application.service.post;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.DeletePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostSearchService implements DeletePostSearchUseCase {
    private final PostSearchRepository postSearchRepository;
    private final PostSearchRepoPort postSearchRepoPort;

    @Override
    public void delete(PostDocument document) {
        postSearchRepoPort.delete(document);
    }
}
