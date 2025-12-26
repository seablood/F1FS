package kr.co.F1FS.app.domain.constructor.presentation.controller.constructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
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
    private final ConstructorUseCase constructorUseCase;
    private final ConstructorRecordRelationUseCase recordRelationUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "컨스트럭터 검색(id)", description = "특정 id의 컨스트럭터를 검색한다.")
    public ResponseEntity<ResponseConstructorDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(constructorUseCase.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "모든 컨스트럭터 검색", description = "모든 컨스트럭터 리스트 반환")
    public ResponseEntity<List<SimpleResponseConstructorDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                                      @RequestParam(value = "condition", defaultValue = "newASC") String condition){
        Page<SimpleResponseConstructorDTO> newPage = constructorUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/constructor-standing")
    @Operation(summary = "컨스트럭터 챔피언십 순위", description = "컨스트럭터 챔피언십 순위 반환")
    public ResponseEntity<List<ResponseConstructorStandingDTO>> getConstructorStandingList(@RequestParam String racingClassCode){
        return ResponseEntity.status(HttpStatus.OK).body(recordRelationUseCase.getConstructorStandingList(racingClassCode));
    }
}
