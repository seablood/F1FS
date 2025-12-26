package kr.co.F1FS.app.domain.post.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.post.application.port.in.admin.AdminPostUseCase;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/post")
@Tag(name = "게시글(Post) 컨트롤러(관리자 권한)", description = "게시글(Post) 관련 기능(관리자 권한)")
public class AdminPostController {
    private final AdminPostUseCase adminPostUseCase;

    @GetMapping("/find-all/{id}")
    @Operation(summary = "특정 유저 게시글 모두 보기", description = "특정 유저가 작성한 게시글들 전부 반환")
    public ResponseEntity<List<ResponseSimplePostDTO>> getPostByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                                     @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                     @PathVariable Long id){
        Page<ResponseSimplePostDTO> newPage = adminPostUseCase.getPostByUser(page, size, condition, id);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게시글 상세 페이지", description = "특정 ID의 게시글 반환")
    public ResponseEntity<ResponsePostDTO> getPostById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostUseCase.getPostById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시글 강제 삭제", description = "관리자 권한으로 특정 게시글 강제 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        adminPostUseCase.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
