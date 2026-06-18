package kr.co.F1FS.app.global.presentation.dto.postRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostRoomDocumentDTO {
    private Long id;
    private String roomTitle;
    private String description;
    private boolean isPublic;
}
