package kr.co.F1FS.app.domain.admin.constructor.application.port.in;

import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.CombinedConstructorRequest;

public interface AdminConstructorUseCase {
    AdminResponseConstructorDTO save(CombinedConstructorRequest request);
}
