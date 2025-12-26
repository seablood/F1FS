package kr.co.F1FS.app.domain.complain.post.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.complain.post.application.port.in.admin.AdminPostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.admin.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/post-complain")
@Tag(name = "게시글 신고(PostComplain) 컨트롤러(관리자 권한)", description = "게시글 신고(PostComplain) 관련 기능(관리자 권한)")
public class AdminPostComplainController {
    private final AdminPostComplainUseCase adminPostComplainUseCase;

    @GetMapping("/post-complain-list")
    @Operation(summary = "게시글 신고 목록", description = "게시글 신고 목록 반환")
    public ResponseEntity<List<AdminResponsePostComplainDTO>> findAllByComplain(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                @RequestParam(value = "condition", defaultValue = "new") String condition){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostComplainUseCase.getAllComplain(
                page, size, condition).getContent());
    }

    @GetMapping("/post-complain/find/{id}")
    @Operation(summary = "게시글 신고 내용", description = "게시글 신고 세부 내용 보기")
    public ResponseEntity<ResponsePostComplainDTO> getComplainById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostComplainUseCase.getComplainById(id));
    }
}
