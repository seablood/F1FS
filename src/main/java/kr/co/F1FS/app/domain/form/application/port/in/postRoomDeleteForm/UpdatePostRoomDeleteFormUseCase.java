package kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface UpdatePostRoomDeleteFormUseCase {
    void updateConfirm(UpdatePostRoomDeleteFormDTO dto, PostRoomDeleteForm deleteForm);
    void modify(ModifyPostRoomDeleteFormDTO dto, PostRoomDeleteForm deleteForm, User user);
}
