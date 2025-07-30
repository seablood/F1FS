package kr.co.F1FS.app.domain.admin.constructor.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;

public interface AdminConstructorPort {
    Constructor save(Constructor constructor);
    void saveAndFlush(Constructor constructor);
    Constructor findById(Long id);
}
