package kr.co.F1FS.app.domain.admin.post.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.admin.post.application.service.AdminPostService;
import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/post")
@Tag(name = "게시글(Post) 컨트롤러(관리자 권한)", description = "게시글(Post) 관련 기능(관리자 권한)")
public class AdminPostController {
    private final AdminPostService adminPostService;

    @GetMapping("/find-all")
    @Operation(summary = "특정 유저 게시글 모두 보기", description = "특정 유저가 작성한 게시글들 전부 반환")
    public ResponseEntity<List<ResponseSimplePostDTO>> getPostByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                                     @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                     @RequestParam(value = "nickname", defaultValue = "") String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostService.getPostByUser(
                page, size, condition, nickname).getContent());
    }

    @GetMapping("/post-complain-list")
    @Operation(summary = "게시글 신고 목록", description = "게시글 신고 목록 반환")
    public ResponseEntity<List<AdminResponsePostComplainDTO>> findAllByComplain(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                @RequestParam(value = "condition", defaultValue = "new") String condition){
        return ResponseEntity.status(HttpStatus.OK).body(adminPostService.getAllComplain(
                page, size, condition).getContent());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시글 강제 삭제", description = "관리자 권한으로 특정 게시글 강제 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        adminPostService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
