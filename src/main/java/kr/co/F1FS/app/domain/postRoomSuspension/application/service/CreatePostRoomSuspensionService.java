package kr.co.F1FS.app.domain.postRoomSuspension.application.service;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.CreatePostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.out.PostRoomSuspensionJpaPort;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostRoomSuspensionService implements CreatePostRoomSuspensionUseCase {
    private final PostRoomSuspensionJpaPort postRoomSuspensionJpaPort;
    private final PostRoomSuspensionDomainService postRoomSuspensionDomainService;

    @Override
    public PostRoomSuspension save(CreatePostRoomSuspensionDTO dto, User suspendUser, PostRoom postRoom) {
        PostRoomSuspension postRoomSuspension = postRoomSuspensionDomainService.createEntity(dto, suspendUser, postRoom);

        return postRoomSuspensionJpaPort.save(postRoomSuspension);
    }
}
