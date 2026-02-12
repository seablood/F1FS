package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.mapper.NoteMapper;
import kr.co.F1FS.app.domain.note.application.port.in.UpdateNoteUseCase;
import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNoteService implements UpdateNoteUseCase {
    private final NoteJpaPort noteJpaPort;
    private final NoteDomainService noteDomainService;
    private final NoteMapper noteMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ResponseNoteDTO modify(Note note, ModifyNoteDTO dto) {
        cacheEvictUtil.evictCachingNote(note);
        noteDomainService.modify(note, dto);

        noteJpaPort.saveAndFlush(note);

        return noteMapper.toResponseNoteDTO(note);
    }

    @Override
    public void updateIsRead(Note note) {
        if(!note.isRead()) noteDomainService.updateIsRead(note);

        noteJpaPort.saveAndFlush(note);
    }
}
