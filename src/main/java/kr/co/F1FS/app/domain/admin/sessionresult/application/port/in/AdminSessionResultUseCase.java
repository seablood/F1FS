package kr.co.F1FS.app.domain.admin.sessionresult.application.port.in;

import kr.co.F1FS.app.domain.admin.sessionresult.presentation.dto.CreateSessionResultDTO;

import java.util.List;

public interface AdminSessionResultUseCase {
    void saveSessionResult(List<CreateSessionResultDTO> dtoList, Long id);
}
