package kr.co.F1FS.app.domain.complain.note.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.complain.note.application.port.in.NoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.CreateNoteComplainDTO;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/note-complain")
@Tag(name = "족지 신고 컨트롤러", description = "쪽지 신고 관련 서비스")
public class NoteComplainController {
    private final NoteComplainUseCase noteComplainUseCase;

    @PostMapping("/save/{id}")
    @Operation(summary = "쪽지 신고", description = "특정 쪽지를 신고")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @Valid @RequestBody CreateNoteComplainDTO dto,
                                     @PathVariable Long id){
        noteComplainUseCase.save(id, principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/find-list")
    @Operation(summary = "신고 목록", description = "쪽지 신고 목록 반환")
    public ResponseEntity<List<ResponseNoteComplainListDTO>> getNoteComplainListByUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                       @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponseNoteComplainListDTO> newPage =
                noteComplainUseCase.getNoteComplainListByUser(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "쪽지 신고 내용", description = "쪽지 신고 세부 내용 보기")
    public ResponseEntity<ResponseNoteComplainDTO> getNoteComplainById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(noteComplainUseCase.getNoteComplainById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "쪽지 신고 삭제", description = "특정 쪽지 신고 삭제")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){
        noteComplainUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
