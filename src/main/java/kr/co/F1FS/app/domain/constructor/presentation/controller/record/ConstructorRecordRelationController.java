package kr.co.F1FS.app.domain.constructor.presentation.controller.record;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/constructor-record")
@Tag(name = "컨스트럭터 레코드 컨트롤러", description = "컨스트럭터 레코드 관련 서비스")
public class ConstructorRecordRelationController {
    private final ConstructorRecordRelationUseCase recordRelationUseCase;

    @GetMapping("/standing")
    @Operation(summary = "컨스트럭터 챔피언십 순위", description = "컨스트럭터 챔피언십 순위 반환")
    public ResponseEntity<List<ResponseConstructorStandingDTO>> getConstructorStandingList(@RequestParam String racingClassCode){
        return ResponseEntity.status(HttpStatus.OK).body(recordRelationUseCase.getConstructorStandingList(racingClassCode));
    }
}
