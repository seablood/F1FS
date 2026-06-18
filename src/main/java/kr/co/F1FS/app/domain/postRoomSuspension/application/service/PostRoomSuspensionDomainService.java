package kr.co.F1FS.app.domain.postRoomSuspension.application.service;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.application.mapper.PostRoomSuspensionMapper;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRoomSuspensionDomainService {
    private final PostRoomSuspensionMapper postRoomSuspensionMapper;

    public PostRoomSuspension createEntity(CreatePostRoomSuspensionDTO dto, User suspendUser, PostRoom postRoom){
        return postRoomSuspensionMapper.toPostRoomSuspension(dto, suspendUser, postRoom);
    }

    public void modify(ModifyPostRoomSuspensionDTO dto, PostRoomSuspension postRoomSuspension){
        postRoomSuspension.modify(dto);
    }
}
