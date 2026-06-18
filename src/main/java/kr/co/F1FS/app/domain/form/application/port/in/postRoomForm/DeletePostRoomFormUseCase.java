package kr.co.F1FS.app.domain.form.application.port.in.postRoomForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeletePostRoomFormUseCase {
    void deleteExpiredForm();
    void delete(PostRoomForm postRoomForm, User user);
}
