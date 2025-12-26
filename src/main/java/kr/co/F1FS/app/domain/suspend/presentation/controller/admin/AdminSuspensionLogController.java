package kr.co.F1FS.app.domain.suspend.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.suspend.application.port.in.admin.AdminSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.presentation.dto.admin.SuspendRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/suspension-log")
@Tag(name = "유저 징계 컨트롤러(관리자 권한)", description = "사용자 징계 관련 서비스(관리자 권한)")
public class AdminSuspensionLogController {
    private final AdminSuspensionLogUseCase adminSuspensionLogUseCase;

    @PutMapping("/suspend")
    @Operation(summary = "유저 징계", description = "신고가 접수된 유저를 징계 부여")
    public ResponseEntity<Void> suspendUser(@Valid @RequestBody SuspendRequestDTO dto){
        adminSuspensionLogUseCase.setSuspend(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
