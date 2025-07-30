package kr.co.F1FS.app.domain.admin.constructor.application.port.in;

import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.CombinedConstructorRequest;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.ModifyConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import org.springframework.data.domain.Page;

public interface AdminConstructorUseCase {
    AdminResponseConstructorDTO save(CombinedConstructorRequest request);
    Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition);
    AdminResponseConstructorDTO getConstructorById(Long id);
    AdminResponseConstructorDTO modify(Long id, ModifyConstructorDTO dto);
}
