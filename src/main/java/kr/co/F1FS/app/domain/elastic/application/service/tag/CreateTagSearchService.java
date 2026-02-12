package kr.co.F1FS.app.domain.elastic.application.service.tag;

import kr.co.F1FS.app.domain.elastic.application.port.in.tag.CreateTagSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.TagSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.TagDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.TagSearchRepository;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTagSearchService implements CreateTagSearchUseCase {
    private final TagSearchRepository tagSearchRepository;
    private final TagSearchRepoPort tagSearchRepoPort;
    private final TagSearchDocumentService tagSearchDocumentService;

    @Override
    public void save(Tag tag) {
        TagDocument tagDocument = tagSearchDocumentService.createEntity(tag);

        tagSearchRepoPort.save(tagDocument);
    }
}
