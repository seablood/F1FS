package kr.co.F1FS.app.global.presentation.dto.suspend;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSuspensionLogDTO {
    private String nickname;
    private int durationDays;
    private String description;
    private String paraphrase;

    public static ResponseSuspensionLogDTO toDto(SuspensionLog log){
        return new ResponseSuspensionLogDTO(log.getSuspendUser().getNickname(), log.getDurationDays(),
                log.getDescription(), log.getParaphrase());
    }
}
