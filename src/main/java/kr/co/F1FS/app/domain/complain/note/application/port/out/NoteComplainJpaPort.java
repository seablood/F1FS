package kr.co.F1FS.app.domain.complain.note.application.port.out;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteComplainJpaPort {
    NoteComplain save(NoteComplain noteComplain);
    Page<NoteComplain> findAll(Pageable pageable);
    NoteComplain findById(Long id);
    Page<NoteComplain> findAllByFromUser(User user, Pageable pageable);
    void delete(NoteComplain noteComplain);
}
