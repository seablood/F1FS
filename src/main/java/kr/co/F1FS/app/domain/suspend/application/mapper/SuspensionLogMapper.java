package kr.co.F1FS.app.domain.suspend.application.mapper;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.suspend.ResponseSuspensionLogDTO;
import org.springframework.stereotype.Component;

@Component
public class SuspensionLogMapper {
    public SuspensionLog toSuspensionLog(CreateSuspensionLogCommand command, User user){
        return SuspensionLog.builder()
                .suspendUser(user)
                .durationDays(command.getDurationDays())
                .suspendUntil(user.getSuspendUntil())
                .description(command.getDescription())
                .paraphrase(command.getParaphrase())
                .build();
    }

    public ResponseSuspensionLogDTO toResponseSuspensionLogDTO(SuspensionLog log){
        return ResponseSuspensionLogDTO.builder()
                .nickname(log.getSuspendUser().getNickname())
                .durationDays(log.getDurationDays())
                .description(log.getDescription())
                .paraphrase(log.getParaphrase())
                .build();
    }
}
