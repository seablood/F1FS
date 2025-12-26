package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.mapper.constructor.ConstructorMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.constructor.ConstructorJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryConstructorService implements QueryConstructorUseCase {
    private final ConstructorJpaPort constructorJpaPort;
    private final ConstructorMapper constructorMapper;

    @Override
    public Constructor findById(Long id) {
        return constructorJpaPort.findById(id);
    }

    @Override
    public Constructor findByName(String name) {
        return constructorJpaPort.findByName(name);
    }

    @Override
    public Page<SimpleResponseConstructorDTO> findAllForSimpleDTO(Pageable pageable) {
        return constructorJpaPort.findAll(pageable)
                .map(constructor -> constructorMapper.toSimpleResponseConstructorDTO(constructor));
    }
}
