package kr.co.F1FS.app.domain.constructor.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.domain.constructor.application.service.ConstructorService;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/constructor")
@Tag(name = "Constructor Controller", description = "컨스트럭터 관련 서비스")
public class ConstructorController {
    private final ConstructorService constructorService;

    @GetMapping("/{id}")
    @Operation(summary = "컨스트럭터 검색(id)", description = "특정 id의 컨스트럭터를 검색한다.")
    public ResponseEntity<ResponseConstructorDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(constructorService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "모든 컨스트럭터 검색", description = "모든 컨스트럭터 리스트 반환")
    public ResponseEntity<List<SimpleResponseConstructorDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                                      @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<SimpleResponseConstructorDTO> newPage = constructorService.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
