package kr.co.F1FS.app.domain.note.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.note.application.port.in.NoteUseCase;
import kr.co.F1FS.app.domain.note.presentation.dto.CreateNoteDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ModifyNoteDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseNoteDTO;
import kr.co.F1FS.app.global.presentation.dto.note.ResponseSimpleNoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/note")
@Tag(name = "쪽지(Note) 시스템", description = "사용자 쪽지 관련 기능")
public class NoteController {
    private final NoteUseCase noteUseCase;

    @PostMapping("/save")
    @Operation(summary = "쪽지 보내기", description = "특정 유저에게 쪽지 보내기 및 저장")
    public ResponseEntity<ResponseNoteDTO> save(@Valid @RequestBody CreateNoteDTO dto, @RequestParam String nickname,
                                                @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(noteUseCase.save(dto, principalDetails.getUser(), nickname));
    }

    @GetMapping("/find-all/receive")
    @Operation(summary = "모든 받은 쪽지 보기", description = "로그인한 유저가 받은 모든 쪽지 페이징으로 반환")
    public ResponseEntity<List<ResponseSimpleNoteDTO>> getNoteByToUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponseSimpleNoteDTO> newPage = noteUseCase.getNoteByToUser(principalDetails.getUser(), page, size);

        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find-all/send")
    @Operation(summary = "모든 보낸 쪽지 보기", description = "로그인한 유저가 보낸 모든 쪽지 페이징으로 반환")
    public ResponseEntity<List<ResponseSimpleNoteDTO>> getNoteByFromUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponseSimpleNoteDTO> newPage = noteUseCase.getNoteByFromUser(principalDetails.getUser(), page, size);

        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "쪽지 상세 페이지", description = "특정 ID의 쪽를 반환")
    public ResponseEntity<ResponseNoteDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(noteUseCase.findByIdDTO(id));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "쪽지 수정", description = "특정 쪽지 내용을 수정")
    public ResponseEntity<ResponseNoteDTO> modify(@Valid @RequestBody ModifyNoteDTO dto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(noteUseCase.modify(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "족지 전송 취소", description = "기존에 전송한 족지를 전송 취소 처리")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        noteUseCase.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
