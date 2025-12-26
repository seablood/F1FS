package kr.co.F1FS.app.domain.elastic.application.service.cdSearch;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CDSearchDocumentService {
    private final DocumentMapper documentMapper;

    public ConstructorDocument createEntity(Constructor constructor){
        return documentMapper.toConstructorDocument(constructor);
    }

    public DriverDocument createEntity(Driver driver){
        return documentMapper.toDriverDocument(driver);
    }

    public void modify(ConstructorDocument document, Constructor constructor){
        document.modify(constructor);
    }

    public void modify(DriverDocument document, Driver driver){
        document.modify(driver);
    }
}
