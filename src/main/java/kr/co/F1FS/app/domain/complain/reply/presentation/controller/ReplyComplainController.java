package kr.co.F1FS.app.domain.complain.reply.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.ReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply-complain")
@Tag(name = "댓글 신고 컨트롤러", description = "댓글 신고 관련 서비스")
public class ReplyComplainController {
    private final ReplyComplainUseCase replyComplainUseCase;

    @PostMapping("/save/{id}")
    @Operation(summary = "댓글 신고", description = "특정 댓글을 신고")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @Valid @RequestBody CreateReplyComplainDTO dto,
                                     @PathVariable Long id){
        replyComplainUseCase.save(id, principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/find-list")
    @Operation(summary = "신고 목록", description = "댓글 신고 목록 반환")
    public ResponseEntity<List<ResponseReplyComplainListDTO>> getReplyComplainListByUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                         @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponseReplyComplainListDTO> newPage = replyComplainUseCase.getReplyComplainListByUser(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "댓글 신고 내용", description = "댓글 신고 세부 내용 보기")
    public ResponseEntity<ResponseReplyComplainDTO> getReplyComplainByUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(replyComplainUseCase.getReplyComplainById(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "댓글 신고 삭제", description = "특정 댓글 신고 삭제")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){
        replyComplainUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
