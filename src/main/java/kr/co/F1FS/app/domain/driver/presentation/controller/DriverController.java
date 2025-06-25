package kr.co.F1FS.app.domain.driver.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.domain.driver.application.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
@Tag(name = "Driver Controller", description = "드라이버 관련 서비스")
public class DriverController {
    private final DriverService driverService;

    @GetMapping("/find/id")
    @Operation(summary = "드라이버 검색(상세 정보)", description = "특정 id의 드라이버를 검색(상세 정보)한다.")
    public ResponseEntity<ResponseDriverDTO> findById(@RequestParam Long id){
        ResponseDriverDTO driverDTO = driverService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }
}
