package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.postRoom.application.mapper.PostRoomMapper;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.CreatePostRoomDTO;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomInfoDTO;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomPublicDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRoomDomainService {
    private final PostRoomMapper postRoomMapper;

    public PostRoom createEntity(CreatePostRoomDTO dto, User user){
        return postRoomMapper.toPostRoom(dto, user);
    }

    public void increasePostCount(PostRoom postRoom){
        postRoom.increasePostCount();
    }

    public void decreasePostCount(PostRoom postRoom){
        postRoom.decreasePostCount();
    }

    public void modifyInfo(ModifyPostRoomInfoDTO dto, PostRoom postRoom){
        postRoom.modifyInfo(dto);
    }

    public void modifyIsPublic(ModifyPostRoomPublicDTO dto, PostRoom postRoom){
        postRoom.modifyIsPublic(dto);
    }

    public boolean certification(User user, PostRoom postRoom){
        return AuthorCertification.certification(user.getUsername(), postRoom.getMasterUser().getUsername());
    }
}
