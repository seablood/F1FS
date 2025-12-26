package kr.co.F1FS.app.domain.constructor.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorDriverUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.constructor.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructorDriverService implements ConstructorDriverUseCase {
    private final ConstructorJpaPort constructorJpaPort;

    @Override
    public Constructor findByNameNotDTONotCache(String name) {
        return constructorJpaPort.findByName(name);
    }
}
