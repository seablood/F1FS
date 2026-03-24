package kr.co.F1FS.app.domain.complain.note.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.complain.note.application.port.in.admin.AdminNoteComplainUseCase;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.note.ResponseNoteComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/note-complain")
@Tag(name = "쪽지 신고(NoteComplain) 컨트롤러(관리자 권한)", description = "쪽지 신고(NoteComplain) 관련 기능(관리자 권한)")
public class AdminNoteComplainController {
    private final AdminNoteComplainUseCase adminNoteComplainUseCase;

    @GetMapping("/list")
    @Operation(summary = "쪽지 신고 목록", description = "쪽지 신고 목록 반환")
    public ResponseEntity<List<ResponseNoteComplainListDTO>> getNoteComplainList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                 @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponseNoteComplainListDTO> newPage = adminNoteComplainUseCase.getNoteComplainList(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "쪽지 신고 내용", description = "쪽지 신고 세부 내용 보기")
    public ResponseEntity<ResponseNoteComplainDTO> getNoteComplainById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminNoteComplainUseCase.getNoteComplainById(id));
    }
}
