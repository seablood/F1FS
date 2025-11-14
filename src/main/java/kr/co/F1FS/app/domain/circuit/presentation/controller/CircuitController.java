package kr.co.F1FS.app.domain.circuit.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/circuit")
@Tag(name = "서킷(Circuit) 시스템", description = "서킷 관련 기능")
public class CircuitController {
    private final CircuitUseCase circuitUseCase;

    @GetMapping("/find-all")
    @Operation(summary = "서킷 리스트", description = "모든 서킷 리스트 반환")
    public ResponseEntity<List<SimpleResponseCircuitDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size){
        Page<SimpleResponseCircuitDTO> newPage = circuitUseCase.findAll(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "서킷 상세 정보", description = "특정 서킷에 대한 상세 정보 반환")
    public ResponseEntity<ResponseCircuitDTO> getCircuitById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(circuitUseCase.getCircuitById(id));
    }
}
