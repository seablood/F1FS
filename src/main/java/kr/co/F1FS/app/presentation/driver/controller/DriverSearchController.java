package kr.co.F1FS.app.presentation.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import kr.co.F1FS.app.application.driver.DriverSearchService;
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
@RequestMapping("/api/v1/search-driver")
@Tag(name = "드라이버 실시간 검색", description = "드라이버를 실시간으로 검색")
public class DriverSearchController {
    private final DriverSearchService driverSearchService;

    @GetMapping("/name")
    @Operation(summary = "드라이버 이름 검색", description = "이름으로 드라이버 검색")
    public ResponseEntity<List<DriverDocument>> findByName(@RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(driverSearchService.searchByName(search));
    }
}
