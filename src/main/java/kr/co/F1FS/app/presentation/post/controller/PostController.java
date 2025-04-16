package kr.co.F1FS.app.presentation.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.presentation.post.dto.CreatePostDTO;
import kr.co.F1FS.app.presentation.post.dto.ModifyPostDTO;
import kr.co.F1FS.app.presentation.post.dto.ResponsePostDTO;
import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.application.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "게시글(Post) 시스템", description = "게시글(Post) 관련 기능")
public class PostController {
    private final PostService postService;

    @PostMapping("/save")
    @Operation(summary = "게시글(Post) 등록", description = "게시글(Post)을 작성하고 등록")
    public ResponseEntity<Post> save(@RequestBody @Valid CreatePostDTO dto,
                                     @AuthenticationPrincipal PrincipalDetails details){
        Post post = postService.save(dto, details.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/find-all")
    @Operation(summary = "모든 게시글 검색(정렬 포함)", description = "존재하는 모든 게시글을 반환")
    public ResponseEntity<List<ResponsePostDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponsePostDTO> newPage = postService.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find-option")
    @Operation(summary = "게시글 옵션 검색", description = "게시글을 제목 또는 내용 옵션으로 검색")
    public ResponseEntity<List<ResponsePostDTO>> findByTitleOrContent(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "option", defaultValue = "title") String option,
            @RequestParam(value = "condition", defaultValue = "new") String condition){
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.findByTitleOrContent(search, page, size, option, condition).getContent()
        );
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게시글 검색(ID)", description = "특정 ID의 게시글을 반환")
    public ResponseEntity<ResponsePostDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.findById(id));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "게시글 수정", description = "특정 ID의 게시글 수정")
    public ResponseEntity<ResponsePostDTO> modify(@PathVariable Long id, @RequestBody @Valid ModifyPostDTO dto,
                                                  @AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(postService.modify(id, dto, details.getUser()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시글 삭제", description = "특정 ID의 게시글 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails details){
        postService.delete(id, details.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
