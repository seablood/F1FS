package kr.co.F1FS.app.presentation.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.application.search.CDSearchService;
import kr.co.F1FS.app.presentation.search.dto.CDSearchSuggestionDTO;
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
@RequestMapping("/api/v1/search-CD")
@Tag(name = "드라이버/컨스트럭터 실시간 검색", description = "드라이버/컨스트럭터를 실시간으로 검색")
public class CDSearchController {
    private final CDSearchService searchService;

    @GetMapping("/live-search")
    @Operation(summary = "드라이버/컨스트럭터 검색(실시간 추천)", description = "실시간 추천 검색")
    public ResponseEntity<List<CDSearchSuggestionDTO>> liveSearch(@RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(searchService.suggestCD(search));
    }

    @GetMapping("/page-search")
    @Operation(summary = "드라이버 이름 검색(검색 페이지)", description = "이름으로 드라이버 검색")
    public ResponseEntity<List<CDSearchSuggestionDTO>> pageSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "condition", defaultValue = "nameASC") String condition,
                                                           @RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchCDWithPaging(
                page, size, condition, search).getContent());
    }
}
