package kr.co.F1FS.app.domain.session.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;

public interface SessionUseCase {
    void save(GrandPrix grandPrix);
    ResponseSessionDTO getSessionByID(Long id);
}
