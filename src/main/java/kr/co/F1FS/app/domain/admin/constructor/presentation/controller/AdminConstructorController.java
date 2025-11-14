package kr.co.F1FS.app.domain.admin.constructor.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.constructor.application.port.in.AdminConstructorUseCase;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.AdminResponseConstructorDTO;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.CombinedConstructorRequest;
import kr.co.F1FS.app.domain.admin.constructor.presentation.dto.ModifyConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/constructor")
@Tag(name = "컨스트럭터 컨트롤러(관리자 권한)", description = "컨스트럭터 관련 서비스(관리자 권한)")
public class AdminConstructorController {
    private final AdminConstructorUseCase adminConstructorUseCase;

    @PostMapping("/save")
    @Operation(summary = "컨스트럭터 생성", description = "컨스트럭터 정보를 입력받아(드라이버 제외) DB에 저장")
    public ResponseEntity<AdminResponseConstructorDTO> save(@Valid @RequestBody CombinedConstructorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminConstructorUseCase.save(request));
    }

    @GetMapping("/find-all")
    @Operation(summary = "컨스트럭터 전체 리스트", description = "컨스트럭터 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseConstructorDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                                      @RequestParam(value = "condition", defaultValue = "nameASC") String condition){
        Page<SimpleResponseConstructorDTO> newPage = adminConstructorUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "컨스트럭터 상세 정보", description = "특정 ID 컨스트럭터 상세 정보 반환")
    public ResponseEntity<AdminResponseConstructorDTO> getConstructorById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminConstructorUseCase.getConstructorById(id));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "컨스트럭터 수정", description = "특정 컨스트럭터 정보 수정")
    public ResponseEntity<AdminResponseConstructorDTO> modify(@PathVariable Long id,
                                                              @Valid @RequestBody ModifyConstructorDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(adminConstructorUseCase.modify(id, dto));
    }
}
