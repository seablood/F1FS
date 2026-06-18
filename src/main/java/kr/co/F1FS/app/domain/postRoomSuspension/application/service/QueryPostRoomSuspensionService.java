package kr.co.F1FS.app.domain.postRoomSuspension.application.service;

import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.QueryPostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.out.PostRoomSuspensionJpaPort;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostRoomSuspensionService implements QueryPostRoomSuspensionUseCase {
    private final PostRoomSuspensionJpaPort postRoomSuspensionJpaPort;

    @Override
    public PostRoomSuspension findByIdWithJoin(Long id) {
        return postRoomSuspensionJpaPort.findByIdWithJoin(id);
    }

    @Override
    public Page<ResponsePostRoomSuspensionListDTO> findPostRoomSuspensionListByPostRoom(Long roomId, Pageable pageable) {
        return postRoomSuspensionJpaPort.findPostRoomSuspensionListByPostRoom(roomId, pageable);
    }
}
