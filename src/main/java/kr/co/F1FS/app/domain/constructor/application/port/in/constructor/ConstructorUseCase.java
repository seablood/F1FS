package kr.co.F1FS.app.domain.constructor.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.CreateConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConstructorUseCase {
    Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition);
    ResponseConstructorDTO findById(Long id);
    Constructor findByNameNotDTONotCache(String name);
    Pageable switchCondition(int page, int size, String condition);
}
