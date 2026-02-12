package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.CreateGrandPrixSuggestSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSuggestSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixSuggestDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSuggestSearchRepository;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGrandPrixSuggestSearchService implements CreateGrandPrixSuggestSearchUseCase {
    private final GrandPrixSuggestSearchRepository suggestSearchRepository;
    private final GrandPrixSuggestSearchRepoPort suggestSearchRepoPort;
    private final GrandPrixSuggestSearchDocumentService suggestSearchDocumentService;

    @Override
    public void save(GrandPrix grandPrix) {
        GrandPrixSuggestDocument document = suggestSearchDocumentService.createEntity(grandPrix);

        suggestSearchRepoPort.save(document);
    }
}
