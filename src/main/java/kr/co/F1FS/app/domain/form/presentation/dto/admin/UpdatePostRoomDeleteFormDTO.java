package kr.co.F1FS.app.domain.form.presentation.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRoomDeleteFormDTO {
    private boolean isConfirmed;
    private String confirmedDescription;
}
