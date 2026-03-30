package kr.co.F1FS.app.domain.elastic.presentation.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.UserSearchUseCase;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDocumentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search-user")
@Tag(name = "유저 실시간 검색", description = "활성화 되어 있는 유저를 실시간으로 검색")
public class UserSearchController {
    private final UserSearchUseCase userSearchUseCase;

    @GetMapping("/live-search")
    @Operation(summary = "유저 닉네임 검색(실시간 추천)", description = "실시간 추천 검색")
    public ResponseEntity<List<ResponseUserDocumentDTO>> liveSearch(@RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(userSearchUseCase.suggestUser(search));
    }

    @GetMapping("/page-search")
    @Operation(summary = "유저 닉네임 검색(검색 페이지)", description = "닉네임으로 유저 검색")
    public ResponseEntity<List<ResponseUserDocumentDTO>> pageSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                                    @RequestParam(value = "condition", defaultValue = "nicknameASC") String condition,
                                                                    @RequestParam(value = "search") String search){
        Page<ResponseUserDocumentDTO> newPage = userSearchUseCase.searchUserWithPaging(page, size, condition, search);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
