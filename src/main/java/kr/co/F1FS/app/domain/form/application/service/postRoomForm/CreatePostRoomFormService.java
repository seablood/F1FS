package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.CreatePostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomForm.PostRoomFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostRoomFormService implements CreatePostRoomFormUseCase {
    private final PostRoomFormDomainService postRoomFormDomainService;
    private final PostRoomFormJpaPort postRoomFormJpaPort;

    @Override
    public PostRoomForm save(CreatePostRoomFormDTO dto, User user) {
        PostRoomForm postRoomForm = postRoomFormDomainService.createEntity(dto, user);

        return postRoomFormJpaPort.save(postRoomForm);
    }
}
