package kr.co.F1FS.app.domain.admin.suggest.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.admin.suggest.application.port.in.AdminSuggestUseCase;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/suggest")
@Tag(name = "Suggest(건의 사항) 컨트롤러(관리자 권한)", description = "건의 사항 관련 서비스(관리자 권한)")
public class AdminSuggestController {
    private final AdminSuggestUseCase adminSuggestUseCase;

    @GetMapping("/find-all")
    @Operation(summary = "모든 건의 사항 검색", description = "모든 건의 사항 리스트 반환")
    public ResponseEntity<List<ResponseSuggestDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size){
        Page<ResponseSuggestDTO> newPage = adminSuggestUseCase.findAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PostMapping("/confirm-toggle/{id}")
    @Operation(summary = "건의 사항 확인 여부", description = "특정 건의 사항에 대한 확인 및 조치 여부 설정")
    public ResponseEntity<Void> confirmedToggle(@PathVariable Long id){
        adminSuggestUseCase.suggestConfirmedToggle(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
