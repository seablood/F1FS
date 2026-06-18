package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.DeletePostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomForm.PostRoomFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeletePostRoomFormService implements DeletePostRoomFormUseCase {
    private final PostRoomFormJpaPort postRoomFormJpaPort;
    private final PostRoomFormDomainService postRoomFormDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Async
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    @Transactional
    @Override
    public void deleteExpiredForm(){
        List<PostRoomForm> list = postRoomFormJpaPort.findAllBeforeOneMonthAgo();

        postRoomFormJpaPort.deleteAll(list);
    }

    @Override
    public void delete(PostRoomForm postRoomForm, User user) {
        cacheEvictUtil.evictCachingPostRoomForm(postRoomForm);

        if(postRoomFormDomainService.certification(user, postRoomForm)){
            throw new PostRoomFormException(PostRoomFormExceptionType.NOT_AUTHORITY_DELETE_POST_ROOM_FORM);
        }

        postRoomFormJpaPort.delete(postRoomForm);
    }
}
