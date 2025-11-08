package kr.co.F1FS.app.domain.constructor.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.application.mapper.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.port.out.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRepository;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConstructorJpaAdapter implements ConstructorJpaPort {
    private final ConstructorRepository constructorRepository;
    private final ConstructorMapper constructorMapper;

    @Override
    public Constructor save(Constructor constructor) {
        return constructorRepository.save(constructor);
    }

    @Override
    public Constructor saveAndFlush(Constructor constructor) {
        return constructorRepository.saveAndFlush(constructor);
    }

    @Override
    public Page<SimpleResponseConstructorDTO> findAll(Pageable pageable) {
        return constructorRepository.findAll(pageable).map(constructor -> constructorMapper.toSimpleResponseConstructorDTO(
                constructor
        ));
    }

    @Override
    public Constructor findById(Long id) {
        return constructorRepository.findById(id)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_NOT_FOUND));
    }
}
