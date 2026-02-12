package kr.co.F1FS.app.domain.complain.note.application.service;

import kr.co.F1FS.app.domain.complain.note.application.port.in.DeleteNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.out.NoteComplainJpaPort;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNoteComplainService implements DeleteNoteComplainUseCase {
    private final NoteComplainJpaPort noteComplainJpaPort;
    private final NoteComplainDomainService noteComplainDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(NoteComplain noteComplain, User user) {
        cacheEvictUtil.evictCachingNoteComplain(noteComplain);

        if(noteComplainDomainService.certification(noteComplain, user)){
            noteComplainJpaPort.delete(noteComplain);
        }
    }
}
