package kr.co.F1FS.app.domain.elastic.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.service.GrandPrixSearchService;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixSearchDTO;
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
@RequestMapping("/api/v1/search-grand-prix")
@Tag(name = "그랑프리 실시간 검색", description = "그랑프리를 실시간으로 검색")
public class GrandPrixSearchController {
    private final GrandPrixSearchService grandPrixSearchService;

    @GetMapping("/live-search")
    @Operation(summary = "그랑프리 검색(실시간 추천)", description = "실시간 추천 검색")
    public ResponseEntity<List<ResponseGrandPrixSearchDTO>> liveSearch(@RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(grandPrixSearchService.suggestGrandPrix(search));
    }

    @GetMapping("/page-search")
    @Operation(summary = "그랑프리 검색(검색 페이지)", description = "검색어로 그랑프리 검색 결과 반환(페이징)")
    public ResponseEntity<List<ResponseGrandPrixSearchDTO>> pageSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                                       @RequestParam(value = "condition", defaultValue = "nameASC") String condition,
                                                                       @RequestParam(value = "search") String search){
        Page<ResponseGrandPrixSearchDTO> newPage = grandPrixSearchService.searchGrandPrixWithPaging(page, size, condition, search);

        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
