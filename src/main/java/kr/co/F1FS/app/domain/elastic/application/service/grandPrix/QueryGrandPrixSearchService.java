package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.QueryGrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryGrandPrixSearchService implements QueryGrandPrixSearchUseCase {
    private final GrandPrixSearchRepository grandPrixSearchRepository;
    private final GrandPrixSearchRepoPort grandPrixSearchRepoPort;

    @Override
    public GrandPrixDocument findById(Long id) {
        return grandPrixSearchRepoPort.findById(id);
    }
}
