package kr.co.F1FS.app.domain.elastic.application.service.cdSearch;

import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.QueryCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.CDSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ConstructorSearchRepository;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryCDSearchService implements QueryCDSearchUseCase {
    private final ConstructorSearchRepository constructorSearchRepository;
    private final DriverSearchRepository driverSearchRepository;
    private final CDSearchRepoPort cdSearchRepoPort;

    @Override
    public ConstructorDocument findConstructorDocumentById(Long id) {
        return cdSearchRepoPort.findConstructorDocumentById(id);
    }

    @Override
    public DriverDocument findDriverDocumentById(Long id) {
        return cdSearchRepoPort.findDriverDocumentById(id);
    }
}
