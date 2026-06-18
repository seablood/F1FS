package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.mapper.FormMapper;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.QueryPostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomForm.PostRoomFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostRoomFormService implements QueryPostRoomFormUseCase {
    private final PostRoomFormJpaPort postRoomFormJpaPort;
    private final FormMapper formMapper;

    @Override
    public PostRoomForm findByIdWithJoin(Long id) {
        return postRoomFormJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponsePostRoomFormDTO findByIdWithJoinForDTO(Long id) {
        return formMapper.toResponsePostRoomFormDTO(postRoomFormJpaPort.findByIdWithJoin(id));
    }

    @Override
    public Page<ResponsePostRoomFormListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return postRoomFormJpaPort.findAllByUser(userId, pageable);
    }

    @Override
    public Page<ResponsePostRoomFormListDTO> findAllByIsConfirmedForDTO(boolean isConfirmed, Pageable pageable) {
        return postRoomFormJpaPort.findAllByIsConfirmed(isConfirmed, pageable);
    }
}
