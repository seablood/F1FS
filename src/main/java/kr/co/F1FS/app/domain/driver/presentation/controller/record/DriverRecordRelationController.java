package kr.co.F1FS.app.domain.driver.presentation.controller.record;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.driver.application.port.in.record.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
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
@RequestMapping("/api/v1/driver-record")
@Tag(name = "드라이버 레코드 컨트롤러", description = "드라이버 레코드 관련 서비스")
public class DriverRecordRelationController {
    private final DriverRecordRelationUseCase recordRelationUseCase;

    @GetMapping("/standing")
    @Operation(summary = "드라이버 챔피언십 순위", description = "드라이버 챔피언십 순위 반환")
    public ResponseEntity<List<ResponseDriverStandingDTO>> getDriverStandingList(@RequestParam String racingClassCode){
        return ResponseEntity.status(HttpStatus.OK).body(recordRelationUseCase.getDriverStandingList(racingClassCode));
    }
}
