package kr.co.F1FS.app.domain.elastic.application.service.cdSearch;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.CreateCDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.CDSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ConstructorSearchRepository;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCDSearchService implements CreateCDSearchUseCase {
    private final CDSearchDocumentService cdSearchDocumentService;
    private final ConstructorSearchRepository constructorSearchRepository;
    private final DriverSearchRepository driverSearchRepository;
    private final CDSearchRepoPort cdSearchRepoPort;

    @Override
    public void save(Constructor constructor) {
        ConstructorDocument document = cdSearchDocumentService.createEntity(constructor);

        cdSearchRepoPort.save(document);
    }

    @Override
    public void save(Driver driver) {
        DriverDocument document = cdSearchDocumentService.createEntity(driver);

        cdSearchRepoPort.save(document);
    }
}
