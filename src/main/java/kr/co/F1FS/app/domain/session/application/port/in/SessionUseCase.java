package kr.co.F1FS.app.domain.session.application.port.in;

import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;

public interface SessionUseCase {
    ResponseSessionDTO getSessionByID(Long id);
}
