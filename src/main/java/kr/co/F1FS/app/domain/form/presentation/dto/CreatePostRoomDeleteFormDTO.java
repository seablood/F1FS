package kr.co.F1FS.app.domain.form.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRoomDeleteFormDTO {
    private String title;
    private String description;
    private boolean isPostDelete;
}
