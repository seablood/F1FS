package kr.co.F1FS.app.domain.postRoomSuspension.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.admin.AdminPostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/post-room-suspension")
@Tag(name = "게시판 이용 차단 시스템(관리자 권한)", description = "게시판 이용 차단 관련 기능(관리자 권한)")
public class AdminPostRoomSuspensionController {
    private final AdminPostRoomSuspensionUseCase adminPostRoomSuspensionUseCase;

    @GetMapping("/find-all/{roomId}")
    @Operation(summary = "게시판 이용 차단 목록", description = "특정 게시판의 이용 차단 목록 반환")
    public ResponseEntity<List<ResponsePostRoomSuspensionListDTO>> getPostRoomSuspensionListByPostRoom(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                                       @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                                                       @PathVariable Long roomId){
        Page<ResponsePostRoomSuspensionListDTO> newPage = adminPostRoomSuspensionUseCase.getPostRoomSuspensionListByPostRoom(page, size, condition, roomId);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "이용 차단 상세 페이지", description = "특정 이용 차단 내역의 상세 페이지 반환")
    public ResponseEntity<ResponsePostRoomSuspensionDTO> getPostRoomSuspensionById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostRoomSuspensionUseCase.getPostRoomSuspensionById(id));
    }
}
