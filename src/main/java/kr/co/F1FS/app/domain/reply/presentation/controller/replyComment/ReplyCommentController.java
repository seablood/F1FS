package kr.co.F1FS.app.domain.reply.presentation.controller.replyComment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.ReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ModifyReplyCommentDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply-comment")
@Tag(name = "대댓글 시스템", description = "대댓글 관련 서비스 기능")
public class ReplyCommentController {
    private final ReplyCommentUseCase replyCommentUseCase;

    @PostMapping("/save/{replyId}")
    @Operation(summary = "대댓글 작성 및 저장", description = "대댓글을 작성 및 저장")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @PathVariable Long replyId, @RequestBody CreateReplyCommentDTO dto){
        replyCommentUseCase.save(dto, principalDetails.getUser(), replyId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/modify/{replyCommentId}")
    @Operation(summary = "대댓글 수정", description = "특정 대댓글을 수정 및 저장")
    public ResponseEntity<ResponseReplyCommentDTO> modify(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          @PathVariable Long replyCommentId,
                                                          @RequestBody ModifyReplyCommentDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                replyCommentUseCase.modify(dto, principalDetails.getUser(), replyCommentId)
        );
    }

    @DeleteMapping("/delete/{replyCommentId}")
    @Operation(summary = "대댓글 삭제", description = "특정 대댓글 삭제")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable Long replyCommentId){
        replyCommentUseCase.delete(replyCommentId, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
