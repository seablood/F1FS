package kr.co.F1FS.app.presentation.admin.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.application.admin.user.AdminUserService;
import kr.co.F1FS.app.presentation.admin.user.dto.ResponseUserComplainDTO;
import kr.co.F1FS.app.presentation.admin.user.dto.SuspendRequestDTO;
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
    private final AdminUserService adminUserService;

    @GetMapping("/user-complain-list")
    @Operation(summary = "유저 신고 목록", description = "유저 신고 목록 반환(페이징)")
    public ResponseEntity<List<ResponseUserComplainDTO>> findAllForComplain(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "condition", defaultValue = "new") String condition
    ){
        Page<ResponseUserComplainDTO> newPage = adminUserService.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PutMapping("/suspend")
    @Operation(summary = "유저 징계", description = "신고가 접수된 유저를 징계 부여")
    public ResponseEntity<Void> suspendUser(@Valid @RequestBody SuspendRequestDTO dto){
        adminUserService.setSuspend(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
