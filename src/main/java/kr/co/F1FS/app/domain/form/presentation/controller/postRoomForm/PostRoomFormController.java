package kr.co.F1FS.app.domain.form.presentation.controller.postRoomForm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.PostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-room-form")
@Tag(name = "게시판(PostRoom) 신청 시스템", description = "게시판 신청 관련 기능")
public class PostRoomFormController {
    private final PostRoomFormUseCase postRoomFormUseCase;

    @PostMapping("/save")
    @Operation(summary = "게시판 신청", description = "게시판 신청 작성 및 저장")
    public ResponseEntity<ResponsePostRoomFormDTO> save(@RequestBody CreatePostRoomFormDTO dto,
                                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(postRoomFormUseCase.save(dto, principalDetails.getUser()));
    }

    @GetMapping("/find-all/loginUser")
    @Operation(summary = "게시판 신청 리스트(로그인 유저)", description = "로그인한 유저의 게시판 신청 리스트 반환")
    public ResponseEntity<List<ResponsePostRoomFormListDTO>> getPostRoomFormByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                   @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponsePostRoomFormListDTO> newPage = postRoomFormUseCase.getPostRoomFormByUser(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게시판 신청 상세 페이지(ID)", description = "특정 ID의 게시판 신청글 반환")
    public ResponseEntity<ResponsePostRoomFormDTO> getPostRoomFormById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomFormUseCase.getPostRoomFormById(id));
    }

    @PatchMapping("/modify/{id}")
    @Operation(summary = "게시판 신청 수정", description = "게시판 신청글 수정 및 저장")
    public ResponseEntity<ResponsePostRoomFormDTO> modify(@RequestBody ModifyPostRoomFormDTO dto,
                                                          @PathVariable Long id,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomFormUseCase.modify(dto, id, principalDetails.getUser()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시판 신청 삭제", description = "특정 ID의 게시판 신청글 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        postRoomFormUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
