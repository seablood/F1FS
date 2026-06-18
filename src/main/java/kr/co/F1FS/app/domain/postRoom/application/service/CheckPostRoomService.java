package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.CheckPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPostRoomService implements CheckPostRoomUseCase {
    private final PostRoomDomainService postRoomDomainService;

    @Override
    public boolean certification(User user, PostRoom postRoom) {
        return postRoomDomainService.certification(user, postRoom);
    }
}
