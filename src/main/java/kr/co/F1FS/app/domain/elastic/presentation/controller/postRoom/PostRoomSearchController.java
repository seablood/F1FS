package kr.co.F1FS.app.domain.elastic.presentation.controller.postRoom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.PostRoomSearchUseCase;
import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDocumentDTO;
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
@RequestMapping("/api/v1/search-post-room")
@Tag(name = "게시판 실시간 검색", description = "게시판을 실시간으로 검색")
public class PostRoomSearchController {
    private final PostRoomSearchUseCase postRoomSearchUseCase;

    @GetMapping("/page-search")
    @Operation(summary = "게시판 검색", description = "특정 키워드를 통해 게시판 검색")
    public ResponseEntity<List<ResponsePostRoomDocumentDTO>> pageSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                                        @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                        @RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomSearchUseCase.getPostRoomList(
                page, size, condition, search).getContent());
    }

    @GetMapping("/page-search/masterUser")
    @Operation(summary = "게시판 검색(게시팜 관리자)", description = "게시판 관리자 닉네임을 통해 게시판 검색")
    public ResponseEntity<List<ResponsePostRoomDocumentDTO>> pageSearchByMasterUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                    @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                                    @RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(postRoomSearchUseCase.getPostRoomListByMasterUser(
                page, size, condition, search).getContent());
    }
}
