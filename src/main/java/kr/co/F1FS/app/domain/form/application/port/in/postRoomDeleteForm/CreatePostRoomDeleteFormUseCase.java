package kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreatePostRoomDeleteFormUseCase {
    PostRoomDeleteForm save(CreatePostRoomDeleteFormDTO dto, User user, PostRoom postRoom);
}
