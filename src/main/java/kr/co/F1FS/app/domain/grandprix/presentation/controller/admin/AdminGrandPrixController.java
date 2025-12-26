package kr.co.F1FS.app.domain.grandprix.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.grandprix.application.port.in.admin.AdminGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.admin.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.admin.ModifyGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/grand-prix")
@Tag(name = "그랑프리(GrandPrix) 시스템(관리자 권한)", description = "그랑프리 관련 기능(관리자 권한)")
public class AdminGrandPrixController {
    private final AdminGrandPrixUseCase adminGrandPrixUseCase;

    @PostMapping("/save")
    @Operation(summary = "그랑프리 생성", description = "그랑프리를 생성 및 저장")
    public ResponseEntity<ResponseGrandPrixDTO> save(@Valid @RequestBody CreateGrandPrixDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminGrandPrixUseCase.save(dto));
    }

    @GetMapping("/find-all")
    @Operation(summary = "그랑프리 전체 리스트", description = "특정 시즌의 그랑프리 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseGrandPrixDTO>> findAll(@RequestParam(value = "season", defaultValue = "2025") Integer season){
        return ResponseEntity.status(HttpStatus.OK).body(adminGrandPrixUseCase.findAll(season));
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "그랑프리 상세 정보", description = "특정 ID 그랑프리 상세 정보 반환")
    public ResponseEntity<ResponseGrandPrixDTO> getGrandPrixById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminGrandPrixUseCase.getGrandPrixById(id));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "그랑프리 수정", description = "특정 ID의 그랑프리 내용 수정")
    public ResponseEntity<ResponseGrandPrixDTO> modify(@Valid @RequestBody ModifyGrandPrixDTO dto,
                                                       @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminGrandPrixUseCase.modify(dto, id));
    }
}
