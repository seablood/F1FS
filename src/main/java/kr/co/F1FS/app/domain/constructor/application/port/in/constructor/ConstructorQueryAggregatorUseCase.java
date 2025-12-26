package kr.co.F1FS.app.domain.constructor.application.port.in.constructor;

import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;

public interface ConstructorQueryAggregatorUseCase {
    ResponseConstructorDTO findByIdForDTO(Long id);
}
