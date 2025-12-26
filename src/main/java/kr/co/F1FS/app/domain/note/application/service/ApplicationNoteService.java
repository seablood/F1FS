package kr.co.F1FS.app.domain.note.application.service;

import kr.co.F1FS.app.domain.note.application.mapper.NoteMapper;
import kr.co.F1FS.app.domain.note.application.port.in.*;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SubscribeNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import kr.co.F1FS.app.global.util.FCMUtil;
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
public class ApplicationNoteService implements NoteUseCase {
    private final CreateNoteUseCase createNoteUseCase;
    private final UpdateNoteUseCase updateNoteUseCase;
    private final DeleteNoteUseCase deleteNoteUseCase;
    private final QueryNoteUseCase queryNoteUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final SubscribeNotificationRedisUseCase subscribeNotificationRedisUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final NoteMapper noteMapper;
    private final FCMUtil fcmUtil;

    @Override
    @Transactional
    public ResponseNoteDTO save(CreateNoteDTO dto, User user, String nickname){
        User toUser = queryUserUseCase.findByNickname(nickname);
        Note note = createNoteUseCase.save(dto, toUser, user);

        sendNotification(toUser, user, note);

        return noteMapper.toResponseNoteDTO(note);
    }

    @Override
    public Page<ResponseSimpleNoteDTO> getNoteByToUser(User user, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return queryNoteUseCase.findAllByToUser(user, pageable);
    }

    @Override
    public Page<ResponseSimpleNoteDTO> getNoteByFromUser(User user, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return queryNoteUseCase.findAllByFromUser(user, pageable);
    }

    @Override
    public Note findByIdNotDTO(Long id){
        return queryNoteUseCase.findById(id);
    }

    @Override
    @Cacheable(value = "NoteDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseNoteDTO findByIdDTO(Long id){
        Note note = findByIdNotDTO(id);
        updateIsRead(note);

        return noteMapper.toResponseNoteDTO(note);
    }

    @Override
    @Transactional
    public void updateIsRead(Note note){
        updateNoteUseCase.updateIsRead(note);
    }

    @Override
    @Transactional
    public ResponseNoteDTO modify(Long id, ModifyNoteDTO dto){
        Note note = findByIdNotDTO(id);
        updateNoteUseCase.modify(note, dto);

        return noteMapper.toResponseNoteDTO(note);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Note note = findByIdNotDTO(id);

        deleteNoteUseCase.delete(note);
    }

    public void sendNotification(User toUser, User fromUser, Note note){
        if(subscribeNotificationRedisUseCase.isSubscribe(toUser.getId(), "note")){
            FCMPushDTO pushDTO = fcmUtil.sendPushForNote(fromUser);
            FCMToken token = fcmUtil.getAuthorToken(toUser);
            fcmLiveUseCase.sendPushForAuthor(pushDTO, token, toUser, note.getId());
        }
    }
}
