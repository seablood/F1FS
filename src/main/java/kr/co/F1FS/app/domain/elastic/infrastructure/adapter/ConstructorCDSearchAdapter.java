package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.application.port.out.ConstructorCDSearchPort;
import kr.co.F1FS.app.domain.elastic.application.service.CDSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConstructorCDSearchAdapter implements ConstructorCDSearchPort {
    private final CDSearchService cdSearchService;

    @Override
    public void save(Constructor constructor) {
        cdSearchService.save(constructor);
    }
}
