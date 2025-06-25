package kr.co.F1FS.app.domain.suspend.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSuspensionLogCommand {
    private String nickname;
    private String description;
    private String paraphrase;
    private Integer durationDays;
}
