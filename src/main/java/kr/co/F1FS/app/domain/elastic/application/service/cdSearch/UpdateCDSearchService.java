package kr.co.F1FS.app.domain.elastic.application.service.cdSearch;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.UpdateCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.CDSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ConstructorSearchRepository;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCDSearchService implements UpdateCDSearchUseCase {
    private final ConstructorSearchRepository constructorSearchRepository;
    private final DriverSearchRepository driverSearchRepository;
    private final CDSearchRepoPort cdSearchRepoPort;
    private final CDSearchDocumentService cdSearchDocumentService;

    @Override
    public void modify(ConstructorDocument document, Constructor constructor) {
        cdSearchDocumentService.modify(document, constructor);

        cdSearchRepoPort.save(document);
    }

    @Override
    public void modify(DriverDocument document, Driver driver) {
        cdSearchDocumentService.modify(document, driver);

        cdSearchRepoPort.save(document);
    }
}
