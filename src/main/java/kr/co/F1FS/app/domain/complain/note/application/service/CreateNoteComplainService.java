package kr.co.F1FS.app.domain.complain.note.application.service;

import kr.co.F1FS.app.domain.complain.note.application.port.in.CreateNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.out.NoteComplainJpaPort;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNoteComplainService implements CreateNoteComplainUseCase {
    private final NoteComplainJpaPort noteComplainJpaPort;
    private final NoteComplainDomainService noteComplainDomainService;

    @Override
    public NoteComplain save(Note note, User user, CreateNoteComplainDTO dto) {
        NoteComplain noteComplain = noteComplainDomainService.createEntity(note, user, dto);
        return noteComplainJpaPort.save(noteComplain);
    }
}
