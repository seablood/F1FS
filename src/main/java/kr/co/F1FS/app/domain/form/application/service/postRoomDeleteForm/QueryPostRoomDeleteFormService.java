package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.mapper.FormMapper;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.QueryPostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm.PostRoomDeleteFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostRoomDeleteFormService implements QueryPostRoomDeleteFormUseCase {
    private final PostRoomDeleteFormJpaPort postRoomDeleteFormJpaPort;
    private final FormMapper formMapper;

    @Override
    public PostRoomDeleteForm findByIdWithJoin(Long id) {
        return postRoomDeleteFormJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponsePostRoomDeleteFormDTO findByIdWithJoinForDTO(Long id) {
        return formMapper.toResponsePostRoomDeleteFormDTO(postRoomDeleteFormJpaPort.findByIdWithJoin(id));
    }

    @Override
    public Page<ResponsePostRoomDeleteFormListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return postRoomDeleteFormJpaPort.findAllByUser(userId, pageable);
    }

    @Override
    public Page<ResponsePostRoomDeleteFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable) {
        return postRoomDeleteFormJpaPort.findAllByIsConfirmed(isConfirmed, pageable);
    }
}
