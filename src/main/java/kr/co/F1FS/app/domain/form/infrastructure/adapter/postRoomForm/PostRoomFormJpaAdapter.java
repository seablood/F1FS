package kr.co.F1FS.app.domain.form.infrastructure.adapter.postRoomForm;

import kr.co.F1FS.app.domain.form.application.port.out.postRoomForm.PostRoomFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.infrastructure.repository.PostRoomFormRepository;
import kr.co.F1FS.app.domain.form.infrastructure.repository.dsl.PostRoomFormDSLRepository;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostRoomFormJpaAdapter implements PostRoomFormJpaPort {
    private final PostRoomFormRepository postRoomFormRepository;
    private final PostRoomFormDSLRepository postRoomFormDSLRepository;

    @Override
    public PostRoomForm save(PostRoomForm postRoomForm) {
        return postRoomFormRepository.save(postRoomForm);
    }

    @Override
    public PostRoomForm saveAndFlush(PostRoomForm postRoomForm) {
        return postRoomFormRepository.saveAndFlush(postRoomForm);
    }

    @Override
    public PostRoomForm findByIdWithJoin(Long id) {
        return postRoomFormDSLRepository.findById(id)
                .orElseThrow(() -> new PostRoomFormException(PostRoomFormExceptionType.POST_ROOM_FORM_NOT_FOUND));
    }

    @Override
    public List<PostRoomForm> findAllBeforeOneMonthAgo() {
        return postRoomFormDSLRepository.findAllBeforeOneMonthAgo();
    }

    @Override
    public Page<ResponsePostRoomFormListDTO> findAllByUser(Long userId, Pageable pageable) {
        return postRoomFormDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public Page<ResponsePostRoomFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable) {
        return postRoomFormDSLRepository.findAllByIsConfirmed(isConfirmed, pageable);
    }

    @Override
    public boolean existsByUser(Long userId) {
        return postRoomFormDSLRepository.existsByUser(userId);
    }

    @Override
    public void delete(PostRoomForm postRoomForm) {
        postRoomFormRepository.delete(postRoomForm);
    }

    @Override
    public void deleteAll(List<PostRoomForm> list) {
        postRoomFormRepository.deleteAll(list);
    }
}
