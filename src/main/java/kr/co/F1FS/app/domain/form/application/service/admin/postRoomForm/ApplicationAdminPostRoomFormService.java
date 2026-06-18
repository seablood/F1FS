package kr.co.F1FS.app.domain.form.application.service.admin.postRoomForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.QueryPostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.UpdatePostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.in.admin.postRoomForm.AdminPostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomFormDTO;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminPostRoomFormService implements AdminPostRoomFormUseCase {
    private final UpdatePostRoomFormUseCase updatePostRoomFormUseCase;
    private final QueryPostRoomFormUseCase queryPostRoomFormUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomFormListDTO> getPostRoomFormListByIsConfirmed(int page, int size, String condition, boolean isConfirmed) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomFormUseCase.findAllByIsConfirmedForDTO(isConfirmed, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "PostRoomFormDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostRoomFormDTO getPostRoomFormById(Long id) {
        return queryPostRoomFormUseCase.findByIdWithJoinForDTO(id);
    }

    @Override
    @Transactional
    public void updateConfirm(UpdatePostRoomFormDTO dto, Long id) {
        PostRoomForm postRoomForm = queryPostRoomFormUseCase.findByIdWithJoin(id);

        updatePostRoomFormUseCase.updateConfirm(dto, postRoomForm);
        fcmLiveUseCase.sendPushAfterPostRoomConfirmed(postRoomForm.getUser(), postRoomForm, id);
    }

    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default:
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
    }
}
