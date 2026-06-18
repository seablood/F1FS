package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.UpdatePostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.out.PostRoomJpaPort;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomInfoDTO;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomPublicDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostRoomService implements UpdatePostRoomUseCase {
    private final PostRoomJpaPort postRoomJpaPort;
    private final PostRoomDomainService postRoomDomainService;

    @Override
    public void increasePostCount(PostRoom postRoom) {
        postRoomDomainService.increasePostCount(postRoom);

        postRoomJpaPort.saveAndFlush(postRoom);
    }

    @Override
    public void decreasePostCount(PostRoom postRoom) {
        postRoomDomainService.decreasePostCount(postRoom);

        postRoomJpaPort.saveAndFlush(postRoom);
    }

    @Override
    public void modifyInfo(ModifyPostRoomInfoDTO dto, PostRoom postRoom, User user) {
        if(!postRoomDomainService.certification(user, postRoom)){
            throw new PostRoomException(PostRoomExceptionType.NOT_AUTHORITY_UPDATE_POST_ROOM);
        }

        postRoomDomainService.modifyInfo(dto, postRoom);
        postRoomJpaPort.saveAndFlush(postRoom);
    }

    @Override
    public void modifyIsPublic(ModifyPostRoomPublicDTO dto, PostRoom postRoom, User user) {
        if(!postRoomDomainService.certification(user, postRoom)){
            throw new PostRoomException(PostRoomExceptionType.NOT_AUTHORITY_UPDATE_POST_ROOM);
        }

        postRoomDomainService.modifyIsPublic(dto, postRoom);
        postRoomJpaPort.saveAndFlush(postRoom);
    }
}
