package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.CreatePostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm.PostRoomDeleteFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostRoomDeleteFormService implements CreatePostRoomDeleteFormUseCase {
    private final PostRoomDeleteFormJpaPort postRoomDeleteFormJpaPort;
    private final PostRoomDeleteFormDomainService postRoomDeleteFormDomainService;

    @Override
    public PostRoomDeleteForm save(CreatePostRoomDeleteFormDTO dto, User user, PostRoom postRoom) {
        PostRoomDeleteForm deleteForm = postRoomDeleteFormDomainService.createEntity(dto, user, postRoom);

        return postRoomDeleteFormJpaPort.save(deleteForm);
    }
}
