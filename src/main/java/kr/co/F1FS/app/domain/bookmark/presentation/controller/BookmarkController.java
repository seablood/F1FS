package kr.co.F1FS.app.domain.bookmark.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.bookmark.application.port.in.BookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@Tag(name = "북마크 시스템", description = "게시글 북마크 관련 서비스")
public class BookmarkController {
    private final BookmarkUseCase bookmarkUseCase;

    @PostMapping("/save/{postId}")
    @Operation(summary = "북마크 저장", description = "특정 게시글 북마크")
    public ResponseEntity<String> save(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        bookmarkUseCase.save(postId, principalDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body("북마크 저장 완료");
    }

    @GetMapping("/find-all")
    @Operation(summary = "북마크 리스트", description = "로그인 유저의 북마크 리스트를 반환")
    public ResponseEntity<List<ResponseBookmarkListDTO>> getBookmarkAllList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                                                            @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponseBookmarkListDTO> newPage = bookmarkUseCase.getBookmarkAllList(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "북마크 삭제", description = "특정 북마크 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        bookmarkUseCase.delete(postId, principalDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
