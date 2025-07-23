package kr.co.F1FS.app.domain.driver.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.driver.application.service.DriverRecordRelationService;
import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.domain.driver.application.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
@Tag(name = "Driver Controller", description = "드라이버 관련 서비스")
public class DriverController {
    private final DriverService driverService;
    private final DriverRecordRelationService recordRelationService;

    @GetMapping("/find/{id}")
    @Operation(summary = "드라이버 검색(상세 정보)", description = "특정 id의 드라이버를 검색(상세 정보)한다.")
    public ResponseEntity<ResponseDriverDTO> findById(@PathVariable Long id){
        ResponseDriverDTO driverDTO = driverService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }

    @GetMapping("/find-name")
    @Operation(summary = "드라이버 검색(상세 정보)", description = "특정 이름의 드라이버를 검색(상세 정보)한다.")
    public ResponseEntity<ResponseDriverDTO> findByEngName(@RequestParam String engName){
        ResponseDriverDTO driverDTO = driverService.findByEngName(engName);
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }

    @GetMapping("/driver-standing")
    @Operation(summary = "드라이버 챔피언십 순위", description = "드라이버 챔피언십 순위 반환")
    public ResponseEntity<List<ResponseDriverStandingDTO>> getDriverStandingList(@RequestParam String racingClassCode){
        return ResponseEntity.status(HttpStatus.OK).body(recordRelationService.getDriverStandingList(racingClassCode));
    }
}
