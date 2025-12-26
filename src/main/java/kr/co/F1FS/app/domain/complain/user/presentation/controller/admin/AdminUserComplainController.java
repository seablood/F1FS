package kr.co.F1FS.app.domain.complain.user.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.complain.user.application.port.in.admin.AdminUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.admin.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/user-complain")
@Tag(name = "유저 신고 컨트롤러(관리자 권한)", description = "사용자 신고 관련 서비스(관리자 권한)")
public class AdminUserComplainController {
    private final AdminUserComplainUseCase adminUserComplainUseCase;

    @GetMapping("/list")
    @Operation(summary = "유저 신고 목록", description = "유저 신고 목록 반환(페이징)")
    public ResponseEntity<List<AdminResponseUserComplainDTO>> findAllForComplain(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "condition", defaultValue = "new") String condition
    ){
        Page<AdminResponseUserComplainDTO> newPage = adminUserComplainUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/search/{search}")
    @Operation(summary = "유저 신고 이력", description = "특정 유저의 신고 당한 이력 반환")
    public ResponseEntity<List<AdminResponseUserComplainDTO>> findComplainByUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "condition", defaultValue = "new") String condition,
            @PathVariable String search
    ){
        Page<AdminResponseUserComplainDTO> newPage = adminUserComplainUseCase.getComplainByUser(page, size, condition, search);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "유저 신고 내용", description = "유저 신고 세부 내용 반환")
    public ResponseEntity<ResponseUserComplainDTO> getComplainById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminUserComplainUseCase.getComplainById(id));
    }
}
