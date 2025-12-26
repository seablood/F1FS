package kr.co.F1FS.app.domain.circuit.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.circuit.application.port.in.admin.AdminCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.CreateCircuitDTO;
import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.ModifyCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/circuit")
@Tag(name = "서킷(Circuit) 시스템(관리자 권한)", description = "서킷 관련 기능(관리자 권한)")
public class AdminCircuitController {
    private final AdminCircuitUseCase adminCircuitUseCase;

    @PostMapping("/save")
    @Operation(summary = "서킷 생성", description = "서킷을 생성하고 저장")
    public ResponseEntity<ResponseCircuitDTO> save(@Valid @RequestBody CreateCircuitDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminCircuitUseCase.save(dto));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "서킷 정보 수정", description = "특정 서킷 정보를 수정 및 저장")
    public ResponseEntity<ResponseCircuitDTO> modify(@PathVariable Long id, @Valid @RequestBody ModifyCircuitDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(adminCircuitUseCase.modify(id, dto));
    }
}
