package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.UpdateGrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSearchRepository;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateGrandPrixSearchService implements UpdateGrandPrixSearchUseCase {
    private final GrandPrixSearchRepository grandPrixSearchRepository;
    private final GrandPrixSearchRepoPort grandPrixSearchRepoPort;
    private final GrandPrixSearchDocumentService grandPrixSearchDocumentService;

    @Override
    public void modify(GrandPrixDocument document, GrandPrix grandPrix) {
        grandPrixSearchDocumentService.modify(document, grandPrix);

        grandPrixSearchRepoPort.save(document);
    }
}
