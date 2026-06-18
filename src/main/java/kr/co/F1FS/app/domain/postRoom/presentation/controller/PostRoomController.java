package kr.co.F1FS.app.domain.postRoom.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.postRoom.application.port.in.PostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.*;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-room")
@Tag(name = "게시판(PostRoom) 시스템", description = "게시판(PostRoom) 관련 기능")
public class PostRoomController {
    private final PostRoomUseCase postRoomUseCase;

    @PostMapping("/save/{formId}")
    @Operation(summary = "게시판 생성", description = "승인된 게시판을 생성")
    public ResponseEntity<ResponsePostRoomDTO> save(@RequestBody CreatePostRoomDTO dto,
                                                    @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                    @PathVariable Long formId){
        return ResponseEntity.status(HttpStatus.CREATED).body(postRoomUseCase.save(dto, principalDetails.getUser(), formId));
    }

    @PostMapping("/verify/{postRoomId}")
    @Operation(summary = "비공개 게시판 인증", description = "비공개 게시판 글 작성을 위한 인증 및 토큰 발행")
    public ResponseEntity<Void> verifyPrivatePostRoom(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestBody VerifyPostRoomDTO dto,
                                                      @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                      @PathVariable Long postRoomId){
        postRoomUseCase.verifyPrivatePostRoom(dto, postRoomId, principalDetails.getUser(), response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-all")
    @Operation(summary = "게시판 리스트", description = "게시판 전체 리스트 반환")
    public ResponseEntity<List<ResponsePostRoomListDTO>> getPostRoomList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                                         @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponsePostRoomListDTO> newPage = postRoomUseCase.getPostRoomList(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find-all/user")
    @Operation(summary = "게시판 리스트(유저)", description = "유저가 관리하는 게시판 리스트 반환")
    public ResponseEntity<List<ResponsePostRoomListDTO>> getPostRoomListByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                                               @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                               @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponsePostRoomListDTO> newPage = postRoomUseCase.getPostRoomListByUser(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PatchMapping("/modify-info/{postRoomId}")
    @Operation(summary = "게시판 정보 수정", description = "게시판 제목, 설명 등 정보 수정")
    public ResponseEntity<ResponsePostRoomDTO> modifyInfo(@RequestBody ModifyPostRoomInfoDTO dto,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          @PathVariable Long postRoomId){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomUseCase.modifyInfo(dto, postRoomId, principalDetails.getUser()));
    }

    @PatchMapping("/modify-is-public/{postRoomId}")
    @Operation(summary = "게시판 공개 여부 수정", description = "특정 게시판의 공개/비공개 여부 수정")
    public ResponseEntity<ResponsePostRoomDTO> modifyIsPublic(@RequestBody ModifyPostRoomPublicDTO dto,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                              @PathVariable Long postRoomId){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomUseCase.modifyIsPublic(dto, postRoomId, principalDetails.getUser()));
    }
}
