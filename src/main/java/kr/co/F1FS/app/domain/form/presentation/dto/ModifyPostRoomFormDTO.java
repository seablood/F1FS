package kr.co.F1FS.app.domain.form.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPostRoomFormDTO {
    private String roomTitle;
    private String description;
    private boolean isPublic;
    private String password;
}
