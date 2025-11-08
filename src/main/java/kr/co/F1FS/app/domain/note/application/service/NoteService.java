package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.mapper.NoteMapper;
import kr.co.F1FS.app.domain.note.application.port.in.NoteUseCase;
import kr.co.F1FS.app.domain.note.application.port.out.NoteJpaPort;
import kr.co.F1FS.app.domain.note.application.port.out.NoteUserPort;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.global.util.exception.note.NoteException;
import kr.co.F1FS.app.global.util.exception.note.NoteExceptionType;
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
public class NoteService implements NoteUseCase {
    private final NoteJpaPort noteJpaPort;
    private final NoteUserPort userPort;
    private final NotificationRedisUseCase redisUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final NoteMapper noteMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public ResponseNoteDTO save(CreateNoteDTO dto, User user, String nickname){
        User toUser = userPort.findByNicknameNotDTO(nickname);
        Note note = noteMapper.toNote(dto, toUser, user);

        noteJpaPort.save(note);

        sendNotification(toUser, user, note);

        return noteMapper.toResponseNoteDTO(noteJpaPort.save(note));
    }

    public Page<ResponseSimpleNoteDTO> getNoteByToUser(User user, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return noteJpaPort.findAllByToUser(user, pageable);
    }

    public Page<ResponseSimpleNoteDTO> getNoteByFromUser(User user, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return noteJpaPort.findAllByFromUser(user, pageable);
    }

    public Note findByIdNotDTO(Long id){
        return noteJpaPort.findById(id);
    }

    @Cacheable(value = "NoteDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseNoteDTO findByIdDTO(Long id){
        Note note = findByIdNotDTO(id);
        updateIsRead(note);

        return noteMapper.toResponseNoteDTO(note);
    }

    @Transactional
    public void updateIsRead(Note note){
        if(!note.isRead()) note.updateIsRead();
        noteJpaPort.saveAndFlush(note);
    }

    @Transactional
    public ResponseNoteDTO modify(Long id, ModifyNoteDTO dto){
        Note note = findByIdNotDTO(id);
        cacheEvictUtil.evictCachingNote(note);

        note.modify(dto);
        noteJpaPort.saveAndFlush(note);

        return noteMapper.toResponseNoteDTO(note);
    }

    @Transactional
    public void delete(Long id){
        Note note = findByIdNotDTO(id);
        if(note.isRead()){
           throw new NoteException(NoteExceptionType.NOTE_IS_READ);
        }
        cacheEvictUtil.evictCachingNote(note);

        noteJpaPort.delete(note);
    }

    public void sendNotification(User toUser, User fromUser, Note note){
        if(redisUseCase.isSubscribe(toUser.getId(), "note")){
            FCMPushDTO pushDTO = fcmUtil.sendPushForNote(fromUser);
            FCMToken token = fcmUtil.getAuthorToken(toUser);
            fcmLiveUseCase.sendPushForAuthor(pushDTO, token, toUser, note.getId());
        }
    }
}
