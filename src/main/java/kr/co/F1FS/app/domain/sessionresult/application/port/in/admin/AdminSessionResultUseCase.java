package kr.co.F1FS.app.domain.sessionresult.application.port.in.admin;

import kr.co.F1FS.app.domain.sessionresult.presentation.dto.admin.CreateSessionResultDTO;

import java.util.List;

public interface AdminSessionResultUseCase {
    void saveSessionResult(List<CreateSessionResultDTO> dtoList, Long id, String racingClassCode);
}
