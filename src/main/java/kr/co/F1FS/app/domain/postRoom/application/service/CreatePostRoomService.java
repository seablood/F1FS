package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.CreatePostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.out.PostRoomJpaPort;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.CreatePostRoomDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostRoomService implements CreatePostRoomUseCase {
    private final PostRoomJpaPort postRoomJpaPort;
    private final PostRoomDomainService postRoomDomainService;

    @Override
    public PostRoom save(CreatePostRoomDTO dto, User user) {
        PostRoom postRoom = postRoomDomainService.createEntity(dto, user);

        return postRoomJpaPort.save(postRoom);
    }
}
