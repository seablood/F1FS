package kr.co.F1FS.app.domain.user.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.user.application.port.in.admin.AdminUserUseCase;
import kr.co.F1FS.app.domain.user.presentation.dto.admin.AdminResponseUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.SimpleResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user")
@Tag(name = "사용자 시스템(관리자 권한)", description = "사용자 관련 서비스(관리자 권한)")
public class AdminUserController {
    private final AdminUserUseCase adminUserUseCase;

    @GetMapping("/find-all")
    @Operation(summary = "모든 유저 검색(정렬 포함)", description = "존재하는 모든 유저 반환")
    public ResponseEntity<List<SimpleResponseUserDTO>> getUserAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                                  @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<SimpleResponseUserDTO> newPage = adminUserUseCase.getUserAll(page, size, condition);

        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "특정 유저 검색(ID)", description = "특정 ID의 유저를 반환")
    public ResponseEntity<AdminResponseUserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserUseCase.getUserById(id));
    }
}
