package kr.co.F1FS.app.presentation.constructor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.presentation.constructor.dto.ResponseConstructorDTO;
import kr.co.F1FS.app.presentation.constructor.dto.ResponseSimpleConstructorDTO;
import kr.co.F1FS.app.application.constructor.ConstructorService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/find/name")
    @Operation(summary = "컨스트럭터 검색(name)", description = "특정 name의 컨스트럭터를 검색한다.")
    public ResponseEntity<List<ResponseSimpleConstructorDTO>> findByName(@RequestParam String search,
                                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(constructorService.findByNameList(search, page, size).getContent());
    }

    @GetMapping("/find/class")
    @Operation(summary = "컨스트럭터 검색(class)", description = "특정 RacingClass의 컨스트럭터를 검색한다.")
    public ResponseEntity<List<ResponseSimpleConstructorDTO>> findByRacingClass(@RequestParam String racingClass,
                                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(constructorService.findByRacingClass(racingClass, page, size)
                .getContent());
    }
}
