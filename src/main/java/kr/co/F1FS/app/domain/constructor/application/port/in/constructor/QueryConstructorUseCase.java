package kr.co.F1FS.app.domain.constructor.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryConstructorUseCase {
    Constructor findById(Long id);
    Constructor findByName(String name);
    Page<SimpleResponseConstructorDTO> findAllForSimpleDTO(Pageable pageable);
}
