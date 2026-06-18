package kr.co.F1FS.app.domain.form.application.port.in.postRoomForm;

import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import org.springframework.data.domain.Page;

public interface PostRoomFormUseCase {
    ResponsePostRoomFormDTO save(CreatePostRoomFormDTO dto, User user);
    Page<ResponsePostRoomFormListDTO> getPostRoomFormByUser(int page, int size, String condition, User user);
    ResponsePostRoomFormDTO getPostRoomFormById(Long id);
    ResponsePostRoomFormDTO modify(ModifyPostRoomFormDTO dto, Long formId, User user);
    void delete(Long formId, User user);
}
