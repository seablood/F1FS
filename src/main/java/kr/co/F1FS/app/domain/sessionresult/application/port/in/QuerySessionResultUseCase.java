package kr.co.F1FS.app.domain.sessionresult.application.port.in;

import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;

import java.util.List;

public interface QuerySessionResultUseCase {
    List<ResponseSessionResultListDTO> findAllBySessionForDTO(Long sessionId);
}
