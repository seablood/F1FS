package kr.co.F1FS.app.domain.reply.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.reply.application.port.in.admin.AdminReplyUseCase;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyByUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reply")
@Tag(name = "댓글 컨트롤러(관리자 권한)", description = "댓글 관련 서비스(관리자 권한)")
public class AdminReplyController {
    private final AdminReplyUseCase adminReplyUseCase;

    @GetMapping("/find/{id}")
    @Operation(summary = "유저 댓글 내역", description = "특정 유저의 댓글 목록 반환")
    public ResponseEntity<List<ResponseReplyByUserDTO>> findReplyByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                                        @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                        @PathVariable Long id){
        Page<ResponseReplyByUserDTO> newPage = adminReplyUseCase.findReplyByUser(page, size, condition, id);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
