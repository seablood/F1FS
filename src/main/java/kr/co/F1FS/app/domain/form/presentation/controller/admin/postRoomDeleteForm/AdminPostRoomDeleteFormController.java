package kr.co.F1FS.app.domain.form.presentation.controller.admin.postRoomDeleteForm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.form.application.port.in.admin.postRoomDeleteForm.AdminPostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/post-room-delete-form")
@Tag(name = "게시판(PostRoom) 삭제 신청 시스템(관리자 권한)", description = "게시판 삭제 신청 관련 기능(관리자 권한)")
public class AdminPostRoomDeleteFormController {
    private final AdminPostRoomDeleteFormUseCase adminPostRoomDeleteFormUseCase;

    @GetMapping("/find-all/confirmed")
    @Operation(summary = "모든 게시판 삭제 신청 리스트", description = "모든 게시판 삭제 신청 리스트를 확인 여부에 따라 반환")
    public ResponseEntity<List<ResponsePostRoomDeleteFormListDTO>> getPostRoomDeleteFormListByIsConfirmed(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                                          @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                                                          @RequestParam(value = "isConfirmed", defaultValue = "true") boolean isConfirmed){
        Page<ResponsePostRoomDeleteFormListDTO> newPage = adminPostRoomDeleteFormUseCase.getPostRoomDeleteFormListByIsConfirmed(page, size, condition, isConfirmed);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게시판 삭제 신청 상세 페이지(ID)", description = "특정 ID의 게시판 삭제 신청글 반환")
    public ResponseEntity<ResponsePostRoomDeleteFormDTO> getPostRoomDeleteFormById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostRoomDeleteFormUseCase.getPostRoomDeleteFormById(id));
    }

    @PatchMapping("/update-confirm/{id}")
    @Operation(summary = "게시판 삭제 승인", description = "특정 게시판의 삭제 승인")
    public ResponseEntity<Void> updateConfirm(@RequestBody UpdatePostRoomDeleteFormDTO dto, @PathVariable Long id){
        adminPostRoomDeleteFormUseCase.updateConfirm(dto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
