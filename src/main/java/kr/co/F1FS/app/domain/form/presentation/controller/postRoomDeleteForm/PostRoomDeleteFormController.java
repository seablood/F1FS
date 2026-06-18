package kr.co.F1FS.app.domain.form.presentation.controller.postRoomDeleteForm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.PostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-room-delete-form")
@Tag(name = "게시판(PostRoom) 삭제 신청 시스템", description = "게시판 삭제 신청 관련 기능")
public class PostRoomDeleteFormController {
    private final PostRoomDeleteFormUseCase postRoomDeleteFormUseCase;

    @PostMapping("/save/{roomId}")
    @Operation(summary = "게시판 삭제 신청", description = "게시판 삭제 신청 작성 및 저장")
    public ResponseEntity<ResponsePostRoomDeleteFormDTO> save(@RequestBody CreatePostRoomDeleteFormDTO dto,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                              @PathVariable Long roomId){
        return ResponseEntity.status(HttpStatus.CREATED).body(postRoomDeleteFormUseCase.save(dto, principalDetails.getUser(), roomId));
    }

    @GetMapping("/find-all/loginUser")
    @Operation(summary = "게시판 삭제 신청 리스트(로그인 유저)", description = "로그인한 유저의 게시판 삭제 리스트 반환")
    public ResponseEntity<List<ResponsePostRoomDeleteFormListDTO>> getPostRoomDeleteFormListByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                                   @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponsePostRoomDeleteFormListDTO> newPage = postRoomDeleteFormUseCase.getPostRoomDeleteFormListByUser(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게시판 삭제 신청 상세 페이지(ID)", description = "특정 ID의 게시판 삭제 신청글 반환")
    public ResponseEntity<ResponsePostRoomDeleteFormDTO> getPostRoomDeleteFormById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomDeleteFormUseCase.getPostRoomDeleteFormById(id));
    }

    @PatchMapping("/modify/{id}")
    @Operation(summary = "게시판 삭제 신청 수정", description = "게시판 삭제 신청글 수정 및 저장")
    public ResponseEntity<ResponsePostRoomDeleteFormDTO> modify(@RequestBody ModifyPostRoomDeleteFormDTO dto,
                                                                @PathVariable Long id,
                                                                @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomDeleteFormUseCase.modify(dto, id, principalDetails.getUser()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시판 삭제 신청글 삭제", description = "특정 ID의 게시판 삭제 신청글 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        postRoomDeleteFormUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
