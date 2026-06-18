package kr.co.F1FS.app.domain.form.application.port.in.postRoomForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface UpdatePostRoomFormUseCase {
    void updateConfirm(UpdatePostRoomFormDTO dto, PostRoomForm postRoomForm);
    void modify(ModifyPostRoomFormDTO dto, PostRoomForm postRoomForm, User user);
}
