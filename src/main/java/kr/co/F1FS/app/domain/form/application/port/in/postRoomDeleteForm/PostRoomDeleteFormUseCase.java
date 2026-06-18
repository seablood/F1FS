package kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import org.springframework.data.domain.Page;

public interface PostRoomDeleteFormUseCase {
    ResponsePostRoomDeleteFormDTO save(CreatePostRoomDeleteFormDTO dto, User user, Long roomId);
    Page<ResponsePostRoomDeleteFormListDTO> getPostRoomDeleteFormListByUser(int page, int size, String condition, User user);
    ResponsePostRoomDeleteFormDTO getPostRoomDeleteFormById(Long formId);
    ResponsePostRoomDeleteFormDTO modify(ModifyPostRoomDeleteFormDTO dto, Long formId, User user);
    void delete(Long formId, User user);
}
