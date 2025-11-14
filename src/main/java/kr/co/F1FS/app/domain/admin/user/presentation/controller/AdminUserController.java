package kr.co.F1FS.app.domain.admin.user.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.user.application.port.in.AdminUserUseCase;
import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.admin.user.presentation.dto.SuspendRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user")
@Tag(name = "유저 컨트롤러(관리자 권한)", description = "사용자 관련 서비스(관리자 권한)")
public class AdminUserController {
    private final AdminUserUseCase adminUserUseCase;

    @GetMapping("/user-complain-list")
    @Operation(summary = "유저 신고 목록", description = "유저 신고 목록 반환(페이징)")
    public ResponseEntity<List<AdminResponseUserComplainDTO>> findAllForComplain(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "condition", defaultValue = "new") String condition
    ){
        Page<AdminResponseUserComplainDTO> newPage = adminUserUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/complain/{search}")
    @Operation(summary = "유저 신고 이력", description = "특정 유저의 신고 당한 이력 반환")
    public ResponseEntity<List<AdminResponseUserComplainDTO>> findComplainByUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "condition", defaultValue = "new") String condition,
            @PathVariable String search
    ){
        Page<AdminResponseUserComplainDTO> newPage = adminUserUseCase.getComplainByUser(page, size, condition, search);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PutMapping("/suspend")
    @Operation(summary = "유저 징계", description = "신고가 접수된 유저를 징계 부여")
    public ResponseEntity<Void> suspendUser(@Valid @RequestBody SuspendRequestDTO dto){
        adminUserUseCase.setSuspend(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
