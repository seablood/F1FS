package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.CDSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ConstructorSearchRepository;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CDSearchRepoAdapter implements CDSearchRepoPort {
    private final DriverSearchRepository driverSearchRepository;
    private final ConstructorSearchRepository constructorSearchRepository;

    @Override
    public DriverDocument save(DriverDocument document) {
        return driverSearchRepository.save(document);
    }

    @Override
    public ConstructorDocument save(ConstructorDocument document) {
        return constructorSearchRepository.save(document);
    }

    @Override
    public ConstructorDocument findConstructorDocumentById(Long id) {
        return constructorSearchRepository.findById(id)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
    }

    @Override
    public DriverDocument findDriverDocumentById(Long id) {
        return driverSearchRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }
}
