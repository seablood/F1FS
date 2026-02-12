package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.TagSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.TagDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.TagSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagSearchRepoAdapter implements TagSearchRepoPort {
    private final TagSearchRepository tagSearchRepository;

    @Override
    public void save(TagDocument tagDocument) {
        tagSearchRepository.save(tagDocument);
    }
}
