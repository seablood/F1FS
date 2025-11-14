package kr.co.F1FS.app.domain.chat.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.chat.application.port.in.ChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat_room")
@Tag(name = "채팅방(CharRoom) 시스템", description = "채팅방 관련 기능")
public class ChatRoomController {
    private final ChatRoomUseCase chatRoomUseCase;

    @PostMapping("/save")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성 및 저장")
    public ResponseEntity<Void> save(@RequestBody CreateChatRoomDTO dto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        chatRoomUseCase.save(dto, principalDetails.getUser().getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/fina-all")
    @Operation(summary = "모든 채팅방 검색(정렬 방식)", description = "존재하는 모든 채팅방을 정렬 방식에 따라 반환")
    public ResponseEntity<List<ResponseChatRoomDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponseChatRoomDTO> newPage = chatRoomUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find-subscribe")
    @Operation(summary = "채팅방 검색(참여 중인 채팅방)", description = "참여 중인 모든 채팅방을 정렬 방식에 따라 반환")
    public ResponseEntity<List<ResponseChatRoomDTO>> findSubscribeChatRoom(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                                           @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                           @AuthenticationPrincipal PrincipalDetails principalDetails){
        Page<ResponseChatRoomDTO> newPage = chatRoomUseCase.findSubscribeChatRoom(page, size, condition, principalDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PatchMapping("/modify/{roomId}")
    @Operation(summary = "채팅방 수정", description = "특정 채팅방 이름 및 정보 수정")
    public ResponseEntity<Void> modify(@PathVariable Long roomId, @RequestBody ModifyChatRoomDTO dto,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        chatRoomUseCase.modify(roomId, dto, principalDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{roomId}")
    @Operation(summary = "채팅방 삭제", description = "특정 채팅방 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long roomId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        chatRoomUseCase.delete(roomId, principalDetails.getUser().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
