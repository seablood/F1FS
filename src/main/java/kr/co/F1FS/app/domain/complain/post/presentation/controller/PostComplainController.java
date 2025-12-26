package kr.co.F1FS.app.domain.complain.post.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.complain.post.application.port.in.PostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-complain")
@Tag(name = "게시글 신고 컨트롤러", description = "게시글 신고 관련 서비스")
public class PostComplainController {
    private final PostComplainUseCase postComplainUseCase;

    @PostMapping("/save/{id}")
    @Operation(summary = "게시글 신고", description = "특정 게시글을 신고")
    public ResponseEntity<Void> postComplain(@Valid @RequestBody CreatePostComplainDTO dto,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @PathVariable Long id){
        postComplainUseCase.postComplain(id, principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-list")
    @Operation(summary = "신고 목록", description = "게시글 신고 목록 반환")
    public ResponseEntity<List<ResponsePostComplainDTO>> findAllByUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                       @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponsePostComplainDTO> newPage = postComplainUseCase.findAllByUser(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게시글 신고 내용", description = "게시글 신고 세부 내용 보기")
    public ResponseEntity<ResponsePostComplainDTO> getPostComplain(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postComplainUseCase.getPostComplain(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시글 신고 삭제", description = "특정 게시글 신고 삭제")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){
        postComplainUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
