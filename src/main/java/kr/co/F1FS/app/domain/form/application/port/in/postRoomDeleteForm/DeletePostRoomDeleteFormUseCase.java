package kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeletePostRoomDeleteFormUseCase {
    void deleteExpiredForm();
    void delete(PostRoomDeleteForm deleteForm, User user);
}
