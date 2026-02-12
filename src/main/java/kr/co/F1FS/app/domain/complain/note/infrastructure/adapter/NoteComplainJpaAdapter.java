package kr.co.F1FS.app.domain.complain.note.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.note.application.port.out.NoteComplainJpaPort;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.infrastructure.repository.NoteComplainRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.note.NoteException;
import kr.co.F1FS.app.global.util.exception.note.NoteExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoteComplainJpaAdapter implements NoteComplainJpaPort {
    private final NoteComplainRepository noteComplainRepository;

    @Override
    public NoteComplain save(NoteComplain noteComplain) {
        return noteComplainRepository.save(noteComplain);
    }

    @Override
    public Page<NoteComplain> findAll(Pageable pageable) {
        return noteComplainRepository.findAll(pageable);
    }

    @Override
    public NoteComplain findById(Long id) {
        return noteComplainRepository.findById(id)
                .orElseThrow(() -> new NoteException(NoteExceptionType.NOTE_COMPLAIN_NOT_FOUND));
    }

    @Override
    public Page<NoteComplain> findAllByFromUser(User user, Pageable pageable) {
        return noteComplainRepository.findAllByFromUser(user, pageable);
    }

    @Override
    public void delete(NoteComplain noteComplain) {
        noteComplainRepository.delete(noteComplain);
    }
}
