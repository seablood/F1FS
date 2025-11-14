package kr.co.F1FS.app.domain.admin.auth.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.auth.application.port.in.AdminAuthUseCase;
import kr.co.F1FS.app.domain.admin.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/auth")
@Tag(name = "관리자 인증", description = "관리자 인증 관련 서비스")
public class AdminAuthController {
    private final AdminAuthUseCase adminAuthUseCase;

    @PostMapping("/admin-save")
    @Operation(summary = "관리자 생성", description = "새로운 관리자 계정 생성")
    public ResponseEntity<ResponseUserDTO> save(@Valid @RequestBody CreateAdminUserDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminAuthUseCase.save(dto));
    }
}
