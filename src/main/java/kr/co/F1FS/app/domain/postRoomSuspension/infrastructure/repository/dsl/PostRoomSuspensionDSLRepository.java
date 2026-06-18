package kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRoomSuspensionDSLRepository {
    Optional<PostRoomSuspension> findById(Long id);
    Page<ResponsePostRoomSuspensionListDTO> findPostRoomSuspensionListByPostRoom(Long roomId, Pageable pageable);
}
