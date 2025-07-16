package kr.co.F1FS.app.domain.admin.sessionresult.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.sessionresult.application.service.AdminSessionResultService;
import kr.co.F1FS.app.domain.admin.sessionresult.presentation.dto.CreateSessionResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/session-result")
@Tag(name = "세션 결과 시스템", description = "각 세션 결과 관련 서비스")
public class AdminSessionResultController {
    private final AdminSessionResultService adminSessionResultService;

    @PostMapping("/save/{id}")
    @Operation(summary = "세션 결과 저장", description = "특정 세션의 결과를 저장")
    public ResponseEntity<Void> save(@Valid @RequestBody List<CreateSessionResultDTO> dtoList, @PathVariable Long id){
        adminSessionResultService.saveSessionResult(dtoList, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
