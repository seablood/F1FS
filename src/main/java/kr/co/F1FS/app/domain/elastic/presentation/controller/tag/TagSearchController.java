package kr.co.F1FS.app.domain.elastic.presentation.controller.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.port.in.tag.TagSearchUseCase;
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
@RequestMapping("/api/v1/search-tag")
@Tag(name = "실시간 태그 검색 시스템", description = "실시간 태그 검색 관련 서비스")
public class TagSearchController {
    private final TagSearchUseCase tagSearchUseCase;

    @GetMapping("/suggest")
    @Operation(summary = "실시간 태그 검색어 추천", description = "실시간으로 태그 검색어를 추천")
    public ResponseEntity<List<String>> getAutoTagList(@RequestParam(value = "keyword") String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(tagSearchUseCase.getAutoTagList(keyword));
    }
}
