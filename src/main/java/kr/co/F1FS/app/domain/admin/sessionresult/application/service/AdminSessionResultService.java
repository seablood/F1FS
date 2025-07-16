package kr.co.F1FS.app.domain.admin.sessionresult.application.service;

import kr.co.F1FS.app.domain.admin.sessionresult.application.mapper.AdminSessionResultMapper;
import kr.co.F1FS.app.domain.admin.sessionresult.application.port.in.AdminSessionResultUseCase;
import kr.co.F1FS.app.domain.admin.sessionresult.presentation.dto.CreateSessionResultDTO;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.SessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSessionResultService implements AdminSessionResultUseCase {
    private final SessionResultUseCase sessionResultUseCase;
    private final AdminSessionResultMapper adminSessionResultMapper;

    @Override
    @Transactional
    public void saveSessionResult(List<CreateSessionResultDTO> dtoList, Long id){
        List<CreateSessionResultCommand> commandList = dtoList.stream()
                .map(dto -> adminSessionResultMapper.toCreateSessionResultCommand(dto))
                .toList();
        sessionResultUseCase.save(commandList, id);
    }
}
