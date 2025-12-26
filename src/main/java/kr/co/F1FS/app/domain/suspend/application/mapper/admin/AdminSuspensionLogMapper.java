package kr.co.F1FS.app.domain.suspend.application.mapper.admin;

import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import kr.co.F1FS.app.domain.suspend.presentation.dto.admin.SuspendRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminSuspensionLogMapper {
    public CreateSuspensionLogCommand toCreateSuspensionLogCommand(SuspendRequestDTO dto){
        return CreateSuspensionLogCommand.builder()
                .nickname(dto.getNickname())
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .durationDays(dto.getDurationDays())
                .build();
    }
}
