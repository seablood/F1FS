package kr.co.F1FS.app.domain.driver.presentation.controller.driver;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.DriverUseCase;
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
@Tag(name = "드라이버 컨트롤러", description = "드라이버 관련 서비스")
public class DriverController {
    private final DriverUseCase driverUseCase;

    @GetMapping("find-all")
    @Operation(summary = "드라이버 전체 리스트", description = "드라이버 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseDriverDTO>> getDriverAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                 @RequestParam(value = "condition", defaultValue = "newASC") String condition){
        Page<SimpleResponseDriverDTO> newPage = driverUseCase.getDriverAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "드라이버 검색(상세 정보)", description = "특정 id의 드라이버를 검색(상세 정보)한다.")
    public ResponseEntity<ResponseDriverDTO> getDriverById(@PathVariable Long id){
        ResponseDriverDTO driverDTO = driverUseCase.getDriverById(id);
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }
}
