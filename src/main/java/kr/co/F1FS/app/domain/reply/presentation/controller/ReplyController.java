package kr.co.F1FS.app.domain.reply.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.reply.application.port.in.ReplyUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.domain.reply.presentation.dto.CreateReplyDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.ModifyReplyDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply")
@Tag(name = "게시글 댓글 시스템", description = "댓글 관련 기능")
public class ReplyController {
    private final ReplyUseCase replyUseCase;

    @PostMapping("/save/{postId}")
    @Operation(summary = "댓글 등록", description = "댓글을 작성하고 등록")
    public ResponseEntity<Void> save(@Valid @RequestBody CreateReplyDTO dto, @PathVariable Long postId,
                                      @AuthenticationPrincipal PrincipalDetails details){
        replyUseCase.save(dto, details.getUser(), postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/find/{postId}")
    @Operation(summary = "댓글 불러오기", description = "특정 게시글의 모든 댓글 리스트로 반환")
    public ResponseEntity<List<ResponseReplyDTO>> findByPostId(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(replyUseCase.findByPost(postId));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "댓글 수정하기", description = "특정 댓글을 수정")
    public ResponseEntity<ResponseReplyDTO> modify(@PathVariable Long id, @Valid @RequestBody ModifyReplyDTO dto,
                                                   @AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(replyUseCase.modify(id, dto, details.getUser()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "댓글 삭제하기", description = "특정 댓글 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails details){
        replyUseCase.delete(id, details.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
