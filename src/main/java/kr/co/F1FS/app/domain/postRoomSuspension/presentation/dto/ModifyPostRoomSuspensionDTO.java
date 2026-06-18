package kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPostRoomSuspensionDTO {
    private int extensionDurationDays;
    private String description;
    private String paraphrase;
}
