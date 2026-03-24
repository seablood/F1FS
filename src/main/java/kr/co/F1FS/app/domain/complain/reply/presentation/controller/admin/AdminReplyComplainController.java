package kr.co.F1FS.app.domain.complain.reply.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.admin.AdminReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reply-complain")
@Tag(name = "댓글 신고(ReplyComplain) 컨트롤러(관리자 권한)", description = "댓글 신고(ReplyComplain) 관련 기능(관리자 권한)")
public class AdminReplyComplainController {
    private final AdminReplyComplainUseCase adminReplyComplainUseCase;

    @GetMapping("/list")
    @Operation(summary = "댓글 신고 목록", description = "댓글 신고 목록 반환")
    public ResponseEntity<List<ResponseReplyComplainListDTO>> getReplyComplainAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                  @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponseReplyComplainListDTO> newPage = adminReplyComplainUseCase.getReplyComplainAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "댓글 신고 내용", description = "댓글 신고 세부 내용 보기")
    public ResponseEntity<ResponseReplyComplainDTO> getReplyComplainById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminReplyComplainUseCase.getReplyComplainById(id));
    }
}
