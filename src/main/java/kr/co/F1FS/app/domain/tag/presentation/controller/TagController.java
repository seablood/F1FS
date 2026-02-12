package kr.co.F1FS.app.domain.tag.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.tag.application.port.in.tag.TagUseCase;
import kr.co.F1FS.app.global.presentation.dto.tag.ResponseTagDTO;
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
@RequestMapping("/api/v1/tag")
@Tag(name = "태그 시스템", description = "태그 관련 서비스")
public class TagController {
    private final TagUseCase tagUseCase;

    @GetMapping("/find-all")
    @Operation(summary = "태그 리스트", description = "모든 태그 리스트 반환")
    public ResponseEntity<List<ResponseTagDTO>> getTagList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size){
        Page<ResponseTagDTO> newPage = tagUseCase.getTagList(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
