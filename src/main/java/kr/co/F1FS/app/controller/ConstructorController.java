package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.dto.CombinedConstructorRequest;
import kr.co.F1FS.app.dto.ResponseConstructorDTO;
import kr.co.F1FS.app.dto.ResponseSimpleConstructorDTO;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.service.ConstructorService;
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

    @PostMapping("/save")
    @Operation(summary = "컨스트럭터 생성", description = "컨스트럭터 정보를 입력받아(드라이버 제외) DB에 저장")
    public ResponseEntity<Constructor> save(@RequestBody CombinedConstructorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(constructorService.save(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "컨스트럭터 검색(id)", description = "특정 id의 컨스트럭터를 검색한다.")
    public ResponseEntity<ResponseConstructorDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(constructorService.findById(id));
    }

    @GetMapping("/find/name")
    @Operation(summary = "컨스트럭터 검색(name)", description = "특정 name의 컨스트럭터를 검색한다.")
    public ResponseEntity<List<ResponseSimpleConstructorDTO>> findByName(@RequestParam String search){
        return ResponseEntity.status(HttpStatus.OK).body(constructorService.findByName(search));
    }

    @GetMapping("/find/class")
    @Operation(summary = "컨스트럭터 검색(class)", description = "특정 RacingClass의 컨스트럭터를 검색한다.")
    public ResponseEntity<List<ResponseSimpleConstructorDTO>> findByRacingClass(@RequestParam String racingClass){
        return ResponseEntity.status(HttpStatus.OK).body(constructorService.findByRacingClass(racingClass));
    }
}
