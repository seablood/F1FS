package kr.co.F1FS.app.domain.reply.presentation.controller.replyLike;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.ReplyLikeRelationUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
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
@RequestMapping("/api/v1/reply-like")
@Tag(name = "댓글 좋아요 시스템", description = "댓글 좋아요 관련 기능")
public class ReplyLikeRelationController {
    private final ReplyLikeRelationUseCase relationUseCase;

    @PostMapping("/toggle/{id}")
    @Operation(summary = "댓글 좋아요 토글 기능", description = "특정 댓글에 대한 좋아요 추가/삭제")
    public ResponseEntity<Void> toggle(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        relationUseCase.toggle(principalDetails.getUser(), id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
