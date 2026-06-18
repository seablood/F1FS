package kr.co.F1FS.app.domain.postRoom.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPostRoomPublicDTO {
    private boolean isPublic;
    private String password;
}
