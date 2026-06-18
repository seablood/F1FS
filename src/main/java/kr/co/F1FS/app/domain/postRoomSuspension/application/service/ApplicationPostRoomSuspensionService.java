package kr.co.F1FS.app.domain.postRoomSuspension.application.service;

import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.in.CheckPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.in.QueryPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.application.mapper.PostRoomSuspensionMapper;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.*;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionException;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationPostRoomSuspensionService implements PostRoomSuspensionUseCase {
    private final CreatePostRoomSuspensionUseCase createPostRoomSuspensionUseCase;
    private final UpdatePostRoomSuspensionUseCase updatePostRoomSuspensionUseCase;
    private final DeletePostRoomSuspensionUseCase deletePostRoomSuspensionUseCase;
    private final QueryPostRoomSuspensionUseCase queryPostRoomSuspensionUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final QueryPostRoomUseCase queryPostRoomUseCase;
    private final CheckPostRoomUseCase checkPostRoomUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final PostRoomSuspensionMapper postRoomSuspensionMapper;

    @Override
    @Transactional
    public ResponsePostRoomSuspensionDTO save(CreatePostRoomSuspensionDTO dto, User user) {
        User suspendUser = queryUserUseCase.findByNickname(dto.getNickname());
        PostRoom postRoom = queryPostRoomUseCase.findByIdWithJoin(dto.getRoomId());
        if(!checkPostRoomUseCase.certification(user, postRoom)){
            throw new PostRoomSuspensionException(PostRoomSuspensionExceptionType.NOT_AUTHORITY_SUSPENSION_SAVE);
        }
        PostRoomSuspension postRoomSuspension = createPostRoomSuspensionUseCase.save(dto, suspendUser, postRoom);
        fcmLiveUseCase.sendPushAfterPostRoomSuspensionSave(postRoomSuspension, postRoom.getId());

        return postRoomSuspensionMapper.toResponsePostRoomSuspensionDTO(postRoomSuspension);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePostRoomSuspensionDTO getPostRoomSuspensionById(Long id) {
        return postRoomSuspensionMapper.toResponsePostRoomSuspensionDTO(queryPostRoomSuspensionUseCase.findByIdWithJoin(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomSuspensionListDTO> getPostRoomSuspensionListByPostRoom(int page, int size, String condition, Long roomId) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomSuspensionUseCase.findPostRoomSuspensionListByPostRoom(roomId, pageable);
    }

    @Override
    @Transactional
    public ResponsePostRoomSuspensionDTO modify(ModifyPostRoomSuspensionDTO dto, Long suspensionId, User user) {
        PostRoomSuspension postRoomSuspension = queryPostRoomSuspensionUseCase.findByIdWithJoin(suspensionId);

        updatePostRoomSuspensionUseCase.modify(dto, postRoomSuspension, user);
        return postRoomSuspensionMapper.toResponsePostRoomSuspensionDTO(postRoomSuspension);
    }

    @Override
    @Transactional
    public void delete(Long id, User user) {
        PostRoomSuspension postRoomSuspension = queryPostRoomSuspensionUseCase.findByIdWithJoin(id);
        User suspendUser = postRoomSuspension.getSuspendUser();
        PostRoom postRoom = postRoomSuspension.getPostRoom();

        deletePostRoomSuspensionUseCase.delete(postRoomSuspension, user);

        fcmLiveUseCase.sendPushAfterPostRoomSuspensionDelete(suspendUser, postRoom, postRoom.getId());
    }

    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default:
                throw new PostRoomException(PostRoomExceptionType.CONDITION_ERROR_POST_ROOM);
        }
    }
}
