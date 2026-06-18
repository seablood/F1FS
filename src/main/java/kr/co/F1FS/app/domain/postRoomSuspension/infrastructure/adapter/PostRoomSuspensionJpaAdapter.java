package kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.adapter;

import kr.co.F1FS.app.domain.postRoomSuspension.application.port.out.PostRoomSuspensionJpaPort;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.repository.PostRoomSuspensionRepository;
import kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.repository.dsl.PostRoomSuspensionDSLRepository;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionException;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRoomSuspensionJpaAdapter implements PostRoomSuspensionJpaPort {
    private final PostRoomSuspensionRepository postRoomSuspensionRepository;
    private final PostRoomSuspensionDSLRepository postRoomSuspensionDSLRepository;

    @Override
    public PostRoomSuspension save(PostRoomSuspension postRoomSuspension) {
        return postRoomSuspensionRepository.save(postRoomSuspension);
    }

    @Override
    public PostRoomSuspension saveAndFlush(PostRoomSuspension postRoomSuspension) {
        return postRoomSuspensionRepository.saveAndFlush(postRoomSuspension);
    }

    @Override
    public PostRoomSuspension findByIdWithJoin(Long id) {
        return postRoomSuspensionDSLRepository.findById(id)
                .orElseThrow(() -> new PostRoomSuspensionException(PostRoomSuspensionExceptionType.POST_ROOM_SUSPENSION_NOT_FOUND));
    }

    @Override
    public Page<ResponsePostRoomSuspensionListDTO> findPostRoomSuspensionListByPostRoom(Long roomId, Pageable pageable) {
        return postRoomSuspensionDSLRepository.findPostRoomSuspensionListByPostRoom(roomId, pageable);
    }

    @Override
    public void delete(PostRoomSuspension postRoomSuspension) {
        postRoomSuspensionRepository.delete(postRoomSuspension);
    }
}
