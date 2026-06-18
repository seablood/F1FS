package kr.co.F1FS.app.domain.form.application.service.admin.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.port.in.admin.postRoomDeleteForm.AdminPostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.QueryPostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.UpdatePostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
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
public class ApplicationAdminPostRoomDeleteFormService implements AdminPostRoomDeleteFormUseCase {
    private final UpdatePostRoomDeleteFormUseCase updatePostRoomDeleteFormUseCase;
    private final QueryPostRoomDeleteFormUseCase queryPostRoomDeleteFormUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomDeleteFormListDTO> getPostRoomDeleteFormListByIsConfirmed(int page, int size, String condition, boolean isConfirmed) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomDeleteFormUseCase.findAllByIsConfirmed(isConfirmed, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "PostRoomDeleteFormDTOForAdmin", key = "#formId", cacheManager = "redisLongCacheManager")
    public ResponsePostRoomDeleteFormDTO getPostRoomDeleteFormById(Long formId) {
        return queryPostRoomDeleteFormUseCase.findByIdWithJoinForDTO(formId);
    }

    @Override
    @Transactional
    public void updateConfirm(UpdatePostRoomDeleteFormDTO dto, Long formId) {
        PostRoomDeleteForm deleteForm = queryPostRoomDeleteFormUseCase.findByIdWithJoin(formId);

        updatePostRoomDeleteFormUseCase.updateConfirm(dto, deleteForm);
        fcmLiveUseCase.sendPushAfterPostRoomDeleteConfirmed(deleteForm.getUser(), deleteForm, formId);
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
