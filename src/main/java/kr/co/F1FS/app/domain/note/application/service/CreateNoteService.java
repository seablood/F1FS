package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.port.in.CreateNoteUseCase;
import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNoteService implements CreateNoteUseCase {
    private final NoteJpaPort noteJpaPort;
    private final NoteDomainService noteDomainService;

    @Override
    public Note save(CreateNoteDTO dto, User toUser, User user) {
        Note note = noteDomainService.createEntity(dto, toUser, user);

        return noteJpaPort.save(note);
    }
}
