package kr.co.F1FS.app.domain.admin.constructor.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.constructor.application.service.AdminConstructorService;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.CombinedConstructorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/constructor")
@Tag(name = "컨스트럭터 컨트롤러(관리자 권한)", description = "컨스트럭터 관련 서비스(관리자 권한)")
public class AdminConstructorController {
    private final AdminConstructorService adminConstructorService;

    @PostMapping("/save")
    @Operation(summary = "컨스트럭터 생성", description = "컨스트럭터 정보를 입력받아(드라이버 제외) DB에 저장")
    public ResponseEntity<AdminResponseConstructorDTO> save(@Valid @RequestBody CombinedConstructorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminConstructorService.save(request));
    }
}
