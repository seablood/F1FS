package kr.co.F1FS.app.domain.sessionresult.application.service.admin;

import kr.co.F1FS.app.domain.sessionresult.application.mapper.admin.AdminSessionResultMapper;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.admin.AdminSessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.admin.CreateSessionResultDTO;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.CreateSessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationAdminSessionResultService implements AdminSessionResultUseCase {
    private final CreateSessionResultUseCase createSessionResultUseCase;
    private final AdminSessionResultMapper adminSessionResultMapper;

    @Override
    @Transactional
    public void saveSessionResult(List<CreateSessionResultDTO> dtoList, Long id, String racingClassCode){
        List<CreateSessionResultCommand> commandList = dtoList.stream()
                .map(dto -> adminSessionResultMapper.toCreateSessionResultCommand(dto))
                .toList();
        createSessionResultUseCase.save(commandList, id, racingClassCode);
    }
}
