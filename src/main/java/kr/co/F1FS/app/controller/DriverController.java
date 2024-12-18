package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.dto.CombinedDriverRequest;
import kr.co.F1FS.app.dto.ResponseDriverDTO;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.DriverRecordRelation;
import kr.co.F1FS.app.service.ConstructorDriverRelationService;
import kr.co.F1FS.app.service.DriverRecordRelationService;
import kr.co.F1FS.app.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
@Tag(name = "Driver Controller", description = "드라이버 관련 서비스")
public class DriverController {
    private final DriverService driverService;
    private final ConstructorDriverRelationService relationService;
    private final DriverRecordRelationService recordRelationService;

    @PostMapping("/save")
    @Operation(summary = "드라이버 생성", description = "드라이버를 생성하고 컨스트럭터 소속을 저장한다.")
    public ResponseEntity<Driver> save(@RequestBody CombinedDriverRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.save(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "드라이버 검색", description = "특정 id의 드라이버를 검색한다.")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        Driver driver = driverService.findById(id);
        DriverRecordRelation relation = recordRelationService.findByDriver(driver);
        map.put("driver", ResponseDriverDTO.toDto(driver));
        map.put("currentSeason", recordRelationService.getCurrentSeason(relation));
        map.put("sinceDebut", recordRelationService.getSinceDebut(relation));

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PutMapping("/fire/{driverId}")
    @Operation(summary = "드라이버 소속 삭제", description = "특정 id의 드라이버를 현재 소속팀에서 삭제한다.")
    public ResponseEntity<Void> fireDriver(@PathVariable Long driverId){
        relationService.delete(driverId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
