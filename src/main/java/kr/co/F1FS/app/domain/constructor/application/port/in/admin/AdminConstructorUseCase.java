package kr.co.F1FS.app.domain.constructor.application.port.in.admin;

import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.CombinedConstructorRequest;
import kr.co.F1FS.app.domain.constructor.presentation.dto.admin.ModifyConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminConstructorUseCase {
    AdminResponseConstructorDTO save(CombinedConstructorRequest request);
    Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition);
    AdminResponseConstructorDTO getConstructorById(Long id);
    AdminResponseConstructorDTO modify(Long id, ModifyConstructorDTO dto);
    Pageable switchCondition(int page, int size, String condition);
}
