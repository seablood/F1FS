package kr.co.F1FS.app.presentation.driver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.presentation.driver.dto.ResponseDriverDTO;
import kr.co.F1FS.app.presentation.driver.dto.ResponseSimpleDriverDTO;
import kr.co.F1FS.app.application.driver.DriverService;
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

    @GetMapping("/find/id")
    @Operation(summary = "드라이버 검색(상세 정보)", description = "특정 id의 드라이버를 검색(상세 정보)한다.")
    public ResponseEntity<ResponseDriverDTO> findById(@RequestParam Long id){
        ResponseDriverDTO driverDTO = driverService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }

    @GetMapping("/find/class")
    @Operation(summary = "드라이버 검색(RacingClass)", description = "특정 클래스에 속한 드라이버들을 검색한다.")
    public ResponseEntity<List<ResponseSimpleDriverDTO>> findByRacingClass(@RequestParam String racingClass,
                                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(driverService.findByRacingClass(racingClass, page, size)
                .getContent());
    }
}
