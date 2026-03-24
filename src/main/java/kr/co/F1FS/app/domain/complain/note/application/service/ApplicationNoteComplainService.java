package kr.co.F1FS.app.domain.complain.note.application.service;

import kr.co.F1FS.app.domain.complain.note.application.port.in.CreateNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.in.DeleteNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.in.NoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.application.port.in.QueryNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.domain.note.application.port.in.QueryNoteUseCase;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.SlackService;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationNoteComplainService implements NoteComplainUseCase {
    private final CreateNoteComplainUseCase createNoteComplainUseCase;
    private final QueryNoteComplainUseCase queryNoteComplainUseCase;
    private final DeleteNoteComplainUseCase deleteNoteComplainUseCase;
    private final QueryNoteUseCase queryNoteUseCase;
    private final SlackService slackService;

    @Override
    @Transactional
    public void save(Long id, User user, CreateNoteComplainDTO dto) {
        Note note = queryNoteUseCase.findByIdWithJoin(id);
        NoteComplain noteComplain = createNoteComplainUseCase.save(note, user, dto);

        sendMessage(noteComplain);
        log.info("게시글 신고 완료 : {}", note.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseNoteComplainListDTO> getNoteComplainListByUser(int page, int size, String condition, User user) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryNoteComplainUseCase.findAllByUserForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "NoteComplainDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseNoteComplainDTO getNoteComplainById(Long id) {
        return queryNoteComplainUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public void delete(Long id, User user) {
        NoteComplain noteComplain = queryNoteComplainUseCase.findByIdWithJoin(id);

        deleteNoteComplainUseCase.delete(noteComplain, user);
    }

    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }

    public void sendMessage(NoteComplain complain){
        String title = "쪽지 신고가 접수되었습니다.";
        HashMap<String, String> data = new HashMap<>();
        data.put("신고자", complain.getFromUser().getNickname());
        data.put("신고된 쪽지", complain.getToNote().getContent());
        data.put("신고 사유", complain.getDescription());

        slackService.sendComplainMessage(title, data);
    }
}
