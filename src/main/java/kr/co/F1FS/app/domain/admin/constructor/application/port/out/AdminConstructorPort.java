package kr.co.F1FS.app.domain.admin.constructor.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminConstructorPort {
    Constructor save(Constructor constructor);
    void saveAndFlush(Constructor constructor);
    Page<SimpleResponseConstructorDTO> findAll(Pageable pageable);
    Constructor findById(Long id);
}
