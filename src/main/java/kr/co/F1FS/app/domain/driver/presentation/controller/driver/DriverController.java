package kr.co.F1FS.app.domain.driver.presentation.controller.driver;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.driver.application.port.in.record.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.DriverUseCase;
import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
@Tag(name = "Driver Controller", description = "드라이버 관련 서비스")
public class DriverController {
    private final DriverUseCase driverUseCase;
    private final DriverRecordRelationUseCase recordRelationUseCase;

    @GetMapping("find-all")
    @Operation(summary = "드라이버 전체 리스트", description = "드라이버 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseDriverDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                 @RequestParam(value = "condition", defaultValue = "newASC") String condition){
        Page<SimpleResponseDriverDTO> newPage = driverUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "드라이버 검색(상세 정보)", description = "특정 id의 드라이버를 검색(상세 정보)한다.")
    public ResponseEntity<ResponseDriverDTO> findById(@PathVariable Long id){
        ResponseDriverDTO driverDTO = driverUseCase.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }

    @GetMapping("/driver-standing")
    @Operation(summary = "드라이버 챔피언십 순위", description = "드라이버 챔피언십 순위 반환")
    public ResponseEntity<List<ResponseDriverStandingDTO>> getDriverStandingList(@RequestParam String racingClassCode){
        return ResponseEntity.status(HttpStatus.OK).body(recordRelationUseCase.getDriverStandingList(racingClassCode));
    }
}
