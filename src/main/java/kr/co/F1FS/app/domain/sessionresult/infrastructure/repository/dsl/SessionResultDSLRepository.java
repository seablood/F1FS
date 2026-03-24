package kr.co.F1FS.app.domain.sessionresult.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.sessionresult.presentation.dto.ResponseSessionResultListDTO;

import java.util.List;

public interface SessionResultDSLRepository {
    List<ResponseSessionResultListDTO> findAllBySession(Long sessionId);
}
