package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorCDSearchPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ConstructorSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConstructorCDSearchAdapter implements AdminConstructorCDSearchPort {
    private final ConstructorSearchRepository constructorSearchRepository;

    @Override
    public void save(ConstructorDocument constructorDocument) {
        constructorSearchRepository.save(constructorDocument);
    }
}
