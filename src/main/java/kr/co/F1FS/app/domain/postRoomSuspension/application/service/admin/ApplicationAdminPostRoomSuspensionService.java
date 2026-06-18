package kr.co.F1FS.app.domain.postRoomSuspension.application.service.admin;

import kr.co.F1FS.app.domain.postRoomSuspension.application.mapper.PostRoomSuspensionMapper;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.QueryPostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.admin.AdminPostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
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
public class ApplicationAdminPostRoomSuspensionService implements AdminPostRoomSuspensionUseCase {
    private final QueryPostRoomSuspensionUseCase queryPostRoomSuspensionUseCase;
    private final PostRoomSuspensionMapper postRoomSuspensionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomSuspensionListDTO> getPostRoomSuspensionListByPostRoom(int page, int size, String condition, Long roomId) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomSuspensionUseCase.findPostRoomSuspensionListByPostRoom(roomId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePostRoomSuspensionDTO getPostRoomSuspensionById(Long id) {
        return postRoomSuspensionMapper.toResponsePostRoomSuspensionDTO(queryPostRoomSuspensionUseCase.findByIdWithJoin(id));
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
