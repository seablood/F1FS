package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.DeletePostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm.PostRoomDeleteFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeletePostRoomDeleteFormService implements DeletePostRoomDeleteFormUseCase {
    private final PostRoomDeleteFormJpaPort postRoomDeleteFormJpaPort;
    private final PostRoomDeleteFormDomainService postRoomDeleteFormDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Async
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    @Transactional
    @Override
    public void deleteExpiredForm(){
        List<PostRoomDeleteForm> list = postRoomDeleteFormJpaPort.findAllBeforeOneMonthAgo();

        postRoomDeleteFormJpaPort.delete(list);
    }

    @Override
    public void delete(PostRoomDeleteForm deleteForm, User user) {
        cacheEvictUtil.evictCachingPostRoomDeleteForm(deleteForm);

        if(postRoomDeleteFormDomainService.certification(user, deleteForm)){
            throw new PostRoomDeleteFormException(PostRoomDeleteFormExceptionType.NOT_AUTHORITY_DELETE_POST_ROOM_DELETE_FORM);
        }

        postRoomDeleteFormJpaPort.delete(deleteForm);
    }
}
