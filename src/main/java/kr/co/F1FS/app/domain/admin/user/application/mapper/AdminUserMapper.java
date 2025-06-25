package kr.co.F1FS.app.domain.admin.user.application.mapper;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.SuspendRequestDTO;
import kr.co.F1FS.app.domain.suspend.presentation.dto.CreateSuspensionLogCommand;
import org.springframework.stereotype.Component;

@Component
public class AdminUserMapper {
    public CreateSuspensionLogCommand toCreateSuspensionLogCommand(SuspendRequestDTO dto){
        return CreateSuspensionLogCommand.builder()
                .nickname(dto.getNickname())
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .durationDays(dto.getDurationDays())
                .build();
    }
}
