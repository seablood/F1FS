package kr.co.F1FS.app.domain.postRoom.application.service.admin;

import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.DeletePostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.QueryPostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.postRoom.application.port.in.DeletePostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.in.QueryPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.in.admin.AdminPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminPostRoomService implements AdminPostRoomUseCase {
    private final DeletePostRoomUseCase deletePostRoomUseCase;
    private final QueryPostRoomUseCase queryPostRoomUseCase;
    private final DeletePostRoomSearchUseCase deletePostRoomSearchUseCase;
    private final QueryPostRoomSearchUseCase queryPostRoomSearchUseCase;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomListDTO> getPostRoomList(int page, int size, String condition) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomUseCase.findPostRoomListForDTO(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomListDTO> getPostRoomListByUser(int page, int size, String condition, Long userId) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomUseCase.findAllByUserForDTO(userId, pageable);
    }

    @Override
    @Transactional
    public void delete(Long roomId) {
        PostRoom postRoom = queryPostRoomUseCase.findById(roomId);
        PostRoomDocument document = queryPostRoomSearchUseCase.findById(roomId);

        deletePostRoomUseCase.delete(postRoom);
        deletePostRoomUseCase.addPostRoomId(roomId);
        deletePostRoomSearchUseCase.delete(document);
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
