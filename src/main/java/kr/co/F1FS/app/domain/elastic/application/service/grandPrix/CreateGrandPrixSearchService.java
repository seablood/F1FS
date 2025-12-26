package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.CreateGrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSearchRepository;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGrandPrixSearchService implements CreateGrandPrixSearchUseCase {
    private final GrandPrixSearchRepository grandPrixSearchRepository;
    private final GrandPrixSearchRepoPort grandPrixSearchRepoPort;
    private final GrandPrixSearchDocumentService grandPrixSearchDocumentService;

    @Override
    public void save(GrandPrix grandPrix) {
        GrandPrixDocument document = grandPrixSearchDocumentService.createEntity(grandPrix);

        grandPrixSearchRepoPort.save(document);
    }
}
