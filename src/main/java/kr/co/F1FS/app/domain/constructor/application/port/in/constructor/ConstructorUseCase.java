package kr.co.F1FS.app.domain.constructor.application.port.in.constructor;

import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConstructorUseCase {
    Page<SimpleResponseConstructorDTO> getConstructorAll(int page, int size, String condition);
    ResponseConstructorDTO getConstructorById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
