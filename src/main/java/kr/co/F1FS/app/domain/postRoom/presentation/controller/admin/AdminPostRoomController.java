package kr.co.F1FS.app.domain.postRoom.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.postRoom.application.port.in.admin.AdminPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/post-room")
@Tag(name = "게시판(PostRoom) 시스템(관리자 권한)", description = "게시판(PostRoom) 관련 기능(관리자 권한)")
public class AdminPostRoomController {
    private final AdminPostRoomUseCase adminPostRoomUseCase;

    @GetMapping("/find-all")
    @Operation(summary = "게시판 리스트", description = "게시판 전체 리스트 반환")
    public ResponseEntity<List<ResponsePostRoomListDTO>> getPostRoomList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                                         @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponsePostRoomListDTO> newPage = adminPostRoomUseCase.getPostRoomList(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find-all/user/{userId}")
    @Operation(summary = "게시판 리스트(유저)", description = "특정 유저가 관리하는 게시판 리스트 반환")
    public ResponseEntity<List<ResponsePostRoomListDTO>> getPostRoomListByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                                               @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                               @PathVariable Long userId){
        Page<ResponsePostRoomListDTO> newPage = adminPostRoomUseCase.getPostRoomListByUser(page, size, condition, userId);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @DeleteMapping("/delete/{roomId}")
    @Operation(summary = "게시판 삭제", description = "삭제 신청이 들어온 게시판을 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long roomId){
        adminPostRoomUseCase.delete(roomId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
