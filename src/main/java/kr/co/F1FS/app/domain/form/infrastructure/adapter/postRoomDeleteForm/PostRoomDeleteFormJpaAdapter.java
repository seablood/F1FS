package kr.co.F1FS.app.domain.form.infrastructure.adapter.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm.PostRoomDeleteFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.infrastructure.repository.PostRoomDeleteFormRepository;
import kr.co.F1FS.app.domain.form.infrastructure.repository.dsl.PostRoomDeleteFormDSLRepository;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRoomDeleteFormJpaAdapter implements PostRoomDeleteFormJpaPort {
    private final PostRoomDeleteFormRepository postRoomDeleteFormRepository;
    private final PostRoomDeleteFormDSLRepository postRoomDeleteFormDSLRepository;

    @Override
    public PostRoomDeleteForm save(PostRoomDeleteForm deleteForm) {
        return postRoomDeleteFormRepository.save(deleteForm);
    }

    @Override
    public PostRoomDeleteForm saveAndFlush(PostRoomDeleteForm deleteForm) {
        return postRoomDeleteFormRepository.saveAndFlush(deleteForm);
    }

    @Override
    public PostRoomDeleteForm findByIdWithJoin(Long id) {
        return postRoomDeleteFormDSLRepository.findById(id)
                .orElseThrow(() -> new PostRoomDeleteFormException(PostRoomDeleteFormExceptionType.POST_ROOM_DELETE_FORM_NOT_FOUND));
    }

    @Override
    public List<PostRoomDeleteForm> findAllBeforeOneMonthAgo() {
        return postRoomDeleteFormDSLRepository.findAllBeforeOneMonthAgo();
    }

    @Override
    public Page<ResponsePostRoomDeleteFormListDTO> findAllByUser(Long userId, Pageable pageable) {
        return postRoomDeleteFormDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public Page<ResponsePostRoomDeleteFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable) {
        return postRoomDeleteFormDSLRepository.findAllByIsConfirmed(isConfirmed, pageable);
    }

    @Override
    public boolean existsByPostRoom(Long roomId) {
        return postRoomDeleteFormDSLRepository.existsByPostRoom(roomId);
    }

    @Override
    public void delete(PostRoomDeleteForm deleteForm) {
        postRoomDeleteFormRepository.delete(deleteForm);
    }

    @Override
    public void delete(List<PostRoomDeleteForm> list) {
        postRoomDeleteFormRepository.deleteAll(list);
    }
}
