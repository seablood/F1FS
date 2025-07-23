package kr.co.F1FS.app.domain.grandprix.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.grandprix.application.service.GrandPrixService;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grand-prix")
@Tag(name = "그랑프리(GrandPrix) 시스템", description = "그랑프리 관련 기능")
public class GrandPrixController {
    private final GrandPrixService grandPrixService;

    @GetMapping("/find-all")
    @Operation(summary = "모든 그랑프리 리스트", description = "모든 그랑프리 리스트를 반환")
    public ResponseEntity<List<SimpleResponseGrandPrixDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                                    @RequestParam(value = "season", defaultValue = "2025") int season){
        Page<SimpleResponseGrandPrixDTO> newPage = grandPrixService.findAll(page, size, season);

        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "그랑프리 상세 내용", description = "특정 ID의 그랑프리 상세 내용을 반환")
    public ResponseEntity<ResponseGrandPrixDTO> getGrandPrixById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(grandPrixService.getGrandPrixById(id));
    }
}
