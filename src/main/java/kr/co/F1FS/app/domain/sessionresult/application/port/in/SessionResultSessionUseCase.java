package kr.co.F1FS.app.domain.sessionresult.application.port.in;

import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;

import java.util.List;

public interface SessionResultSessionUseCase {
    List<ResponseSessionResultDTO> getSessionResultBySession(Session session);
}
