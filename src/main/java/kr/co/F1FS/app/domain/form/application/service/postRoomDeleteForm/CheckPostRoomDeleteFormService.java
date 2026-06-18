package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.CheckPostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm.PostRoomDeleteFormJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPostRoomDeleteFormService implements CheckPostRoomDeleteFormUseCase {
    private final PostRoomDeleteFormJpaPort postRoomDeleteFormJpaPort;

    @Override
    public boolean existsByPostRoom(Long roomId) {
        return postRoomDeleteFormJpaPort.existsByPostRoom(roomId);
    }
}
