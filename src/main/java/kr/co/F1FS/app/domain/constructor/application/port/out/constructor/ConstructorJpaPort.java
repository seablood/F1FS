package kr.co.F1FS.app.domain.constructor.application.port.out.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConstructorJpaPort {
    Constructor save(Constructor constructor);
    Constructor saveAndFlush(Constructor constructor);
    Page<Constructor> findAll(Pageable pageable);
    Constructor findById(Long id);
    Constructor findByName(String name);
}
