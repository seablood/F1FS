package kr.co.F1FS.app.presentation.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.application.post.PostLikeRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-like")
@Tag(name = "게시글 좋아요 시스템", description = "게시글 좋아요 관련 기능")
public class PostLikeController {
    private final PostLikeRelationService relationService;

    @PostMapping("/toggle/{id}")
    @Operation(summary = "좋아요 토글 기능", description = "특정 게시글에 대한 좋아요 추가/삭제")
    public ResponseEntity<Void> toggle(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails details){
        relationService.toggle(details.getUser(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
