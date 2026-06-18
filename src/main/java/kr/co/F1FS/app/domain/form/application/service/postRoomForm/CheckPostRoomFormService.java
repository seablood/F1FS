package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.CheckPostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomForm.PostRoomFormJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPostRoomFormService implements CheckPostRoomFormUseCase {
    private final PostRoomFormJpaPort postRoomFormJpaPort;

    @Override
    public boolean existsByUser(Long userId) {
        return postRoomFormJpaPort.existsByUser(userId);
    }
}
