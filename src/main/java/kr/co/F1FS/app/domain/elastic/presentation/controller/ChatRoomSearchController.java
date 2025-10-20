package kr.co.F1FS.app.domain.elastic.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.service.ChatRoomSearchService;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDocumentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search-chat-room")
@Tag(name = "게시글 실시간 검색", description = "게시글을 실시간으로 검색")
public class ChatRoomSearchController {
    private final ChatRoomSearchService chatRoomSearchService;

    @GetMapping("/page-search")
    @Operation(summary = "채팅방 검색", description = "특정 키워드를 통해 채팅방 검색")
    public ResponseEntity<List<ResponseChatRoomDocumentDTO>> pageSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                                        @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                        @RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(chatRoomSearchService.getChatRoomList(
                page, size, condition, search).getContent());
    }
}
