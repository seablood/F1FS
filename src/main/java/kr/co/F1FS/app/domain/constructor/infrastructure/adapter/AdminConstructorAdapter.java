package kr.co.F1FS.app.domain.constructor.infrastructure.adapter;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.admin.constructor.application.port.out.AdminConstructorPort;
import kr.co.F1FS.app.domain.constructor.application.mapper.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.service.ConstructorService;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRepository;
import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConstructorAdapter implements AdminConstructorPort {
    private final ConstructorRepository constructorRepository;
    private final ConstructorMapper constructorMapper;

    @Override
    public Constructor save(Constructor constructor) {
        return constructorRepository.save(constructor);
    }

    @Override
    public void saveAndFlush(Constructor constructor) {
        constructorRepository.saveAndFlush(constructor);
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
