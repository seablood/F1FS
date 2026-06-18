package kr.co.F1FS.app.domain.postRoomSuspension.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.CheckPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.DeletePostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.out.PostRoomSuspensionJpaPort;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionException;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostRoomSuspensionService implements DeletePostRoomSuspensionUseCase {
    private final PostRoomSuspensionJpaPort postRoomSuspensionJpaPort;
    private final CheckPostRoomUseCase checkPostRoomUseCase;

    @Override
    public void delete(PostRoomSuspension postRoomSuspension, User user) {
        if(!checkPostRoomUseCase.certification(user, postRoomSuspension.getPostRoom())){
            throw new PostRoomSuspensionException(PostRoomSuspensionExceptionType.NOT_AUTHORITY_SUSPENSION_DELETE);
        }

        postRoomSuspensionJpaPort.delete(postRoomSuspension);
    }
}
