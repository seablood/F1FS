package kr.co.F1FS.app.domain.complain.post.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.complain.post.application.service.PostComplainService;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-complain")
@Tag(name = "게시글 신고 컨트롤러", description = "게시글 신고 관련 서비스")
public class PostComplainController {
    private final PostComplainService postComplainService;

    @PostMapping("/save/{id}")
    @Operation(summary = "게시글 신고", description = "특정 게시글을 신고")
    public ResponseEntity<Void> postComplain(@Valid @RequestBody CreatePostComplainDTO dto,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @PathVariable Long id){
        postComplainService.postComplain(id, principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
