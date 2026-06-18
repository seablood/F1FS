package kr.co.F1FS.app.global.presentation.dto.postRoomSuspension;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostRoomSuspensionDTO {
    private Long id;
    private String suspendUser;
    private String postRoom;
    private int durationDays;
    private String description;
    private String paraphrase;
}
