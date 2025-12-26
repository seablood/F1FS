package kr.co.F1FS.app.domain.suspend.application.service;

import kr.co.F1FS.app.domain.suspend.application.mapper.SuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspensionLogDomainService {
    private final SuspensionLogMapper suspensionLogMapper;

    public SuspensionLog createEntity(CreateSuspensionLogCommand command, User user){
        return suspensionLogMapper.toSuspensionLog(command, user);
    }
}
