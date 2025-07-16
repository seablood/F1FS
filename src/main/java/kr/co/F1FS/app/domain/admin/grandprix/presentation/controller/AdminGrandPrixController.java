package kr.co.F1FS.app.domain.admin.grandprix.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.grandprix.application.service.AdminGrandPrixService;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.ModifyGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/grand-prix")
@Tag(name = "그랑프리(GrandPrix) 시스템(관리자 권한)", description = "그랑프리 관련 기능(관리자 권한)")
public class AdminGrandPrixController {
    private final AdminGrandPrixService adminGrandPrixService;

    @PostMapping("/save")
    @Operation(summary = "그랑프리 생성", description = "그랑프리를 생성 및 저장")
    public ResponseEntity<ResponseGrandPrixDTO> save(@Valid @RequestBody CreateGrandPrixDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminGrandPrixService.save(dto));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "그랑프리 수정", description = "특정 ID의 그랑프리 내용 수정")
    public ResponseEntity<ResponseGrandPrixDTO> modify(@Valid @RequestBody ModifyGrandPrixDTO dto,
                                                       @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminGrandPrixService.modify(dto, id));
    }
}
