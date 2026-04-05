package kr.co.F1FS.app.domain.elastic.presentation.controller.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.BookmarkSearchUseCase;
import kr.co.F1FS.app.global.presentation.dto.bookmark.ResponseBookmarkDocumentDTO;
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
@RequestMapping("/api/v1/search-bookmark")
@Tag(name = "북마크 실시간 검색", description = "즐겨찾기를 실시간으로 검색")
public class BookmarkSearchController {
    private final BookmarkSearchUseCase bookmarkSearchUseCase;

    @GetMapping("/page-search")
    @Operation(summary = "북마크 검색", description = "특정 키워드를 통해 즐겨찾기 검색")
    public ResponseEntity<List<ResponseBookmarkDocumentDTO>> getBookmarkList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                                             @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                             @RequestParam(value = "search") String search){
        Page<ResponseBookmarkDocumentDTO> newPage = bookmarkSearchUseCase.getBookmarkList(page, size, condition, search);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
