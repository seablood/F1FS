package kr.co.F1FS.app.presentation.suspend.dto;

import kr.co.F1FS.app.domain.model.rdb.SuspensionLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
