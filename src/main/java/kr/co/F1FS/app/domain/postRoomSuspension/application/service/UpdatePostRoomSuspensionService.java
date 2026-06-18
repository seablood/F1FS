package kr.co.F1FS.app.domain.postRoomSuspension.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.CheckPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.UpdatePostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.out.PostRoomSuspensionJpaPort;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionException;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostRoomSuspensionService implements UpdatePostRoomSuspensionUseCase {
    private final PostRoomSuspensionJpaPort postRoomSuspensionJpaPort;
    private final PostRoomSuspensionDomainService postRoomSuspensionDomainService;
    private final CheckPostRoomUseCase checkPostRoomUseCase;

    @Override
    public void modify(ModifyPostRoomSuspensionDTO dto, PostRoomSuspension postRoomSuspension, User user) {
        if(!checkPostRoomUseCase.certification(user, postRoomSuspension.getPostRoom())){
            throw new PostRoomSuspensionException(PostRoomSuspensionExceptionType.NOT_AUTHORITY_SUSPENSION_UPDATE);
        }

        postRoomSuspensionDomainService.modify(dto, postRoomSuspension);
        postRoomSuspensionJpaPort.saveAndFlush(postRoomSuspension);
    }
}
