package kr.co.F1FS.app.domain.elastic.presentation.controller.grandPrix;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.GrandPrixSuggestSearchUseCase;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseSuggestGrandPrixSearchDTO;
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
@RequestMapping("/api/v1/search-grand-prix-suggest")
@Tag(name = "그랑프리 실시간 검색 시스템", description = "그랑프리 실시간 검색 관련 서비스")
public class GrandPrixSuggestSearchController {
    private final GrandPrixSuggestSearchUseCase suggestSearchUseCase;

    @GetMapping("/auto-keyword")
    @Operation(summary = "실시간 그랑프리 검색어 추천", description = "실시간으로 그랑프리 검색어를 추천해 반환")
    public ResponseEntity<List<ResponseSuggestGrandPrixSearchDTO>> getAutoGrandPrixList(@RequestParam(value = "keyword") String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(suggestSearchUseCase.getAutoGrandPrixList(keyword));
    }

    @GetMapping("/page-search")
    @Operation(summary = "그랑프리 페이징 검색", description = "검색어로 그랑프리 검색 결과 반환(페이징)")
    public ResponseEntity<List<ResponseSuggestGrandPrixSearchDTO>> getGrandPrixList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                    @RequestParam(value = "condition", defaultValue = "nameASC") String condition,
                                                                                    @RequestParam(value = "keyword") String keyword){
        Page<ResponseSuggestGrandPrixSearchDTO> newPage = suggestSearchUseCase.getGrandPrixList(page, size, condition, keyword);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
