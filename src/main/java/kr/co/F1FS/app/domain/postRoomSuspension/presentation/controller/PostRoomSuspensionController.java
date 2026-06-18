package kr.co.F1FS.app.domain.postRoomSuspension.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.PostRoomSuspensionUseCase;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-room-suspension")
@Tag(name = "게시판 이용 차단 시스템", description = "게시판 이용 차단 관련 기능")
public class PostRoomSuspensionController {
    private final PostRoomSuspensionUseCase postRoomSuspensionUseCase;

    @PostMapping("/save")
    @Operation(summary = "게시판 이용 차단 추가", description = "특정 유저의 게시판 이용 차단")
    public ResponseEntity<ResponsePostRoomSuspensionDTO> save(@RequestBody CreatePostRoomSuspensionDTO dto,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(postRoomSuspensionUseCase.save(dto, principalDetails.getUser()));
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "이용 차단 상세 페이지", description = "특정 이용 차단 내역의 상세 페이지 반환")
    public ResponseEntity<ResponsePostRoomSuspensionDTO> getPostRoomSuspensionById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomSuspensionUseCase.getPostRoomSuspensionById(id));
    }

    @GetMapping("/find-all/{roomId}")
    @Operation(summary = "게시판 이용 차단 목록", description = "특정 게시판의 이용 차단 목록 반환")
    public ResponseEntity<List<ResponsePostRoomSuspensionListDTO>> getPostRoomSuspensionListByPostRoom(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                                       @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                                                       @PathVariable Long roomId){
        Page<ResponsePostRoomSuspensionListDTO> newPage = postRoomSuspensionUseCase.getPostRoomSuspensionListByPostRoom(page, size, condition, roomId);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PatchMapping("/modify/{id}")
    @Operation(summary = "게시판 이용 차단 내용 수정", description = "특정 이용 차단 내역의 상세 내용 수정")
    public ResponseEntity<ResponsePostRoomSuspensionDTO> modify(@RequestBody ModifyPostRoomSuspensionDTO dto,
                                                                @PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomSuspensionUseCase.modify(dto, id, principalDetails.getUser()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시판 이용 차단 해제", description = "특정 이용 차단 해제")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        postRoomSuspensionUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
