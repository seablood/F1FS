package kr.co.F1FS.app.domain.constructor.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.application.service.ConstructorService;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRepository;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationConstructorPort;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CDRelationConstructorAdapter implements CDRelationConstructorPort {
    private final ConstructorService constructorService;
    private final ConstructorRepository constructorRepository;

    @Override
    public Constructor findByName(String name) {
        return constructorRepository.findByName(name)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
    }
}
