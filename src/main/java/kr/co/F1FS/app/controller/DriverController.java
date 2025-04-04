package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.dto.CombinedDriverRequest;
import kr.co.F1FS.app.dto.ResponseDriverDTO;
import kr.co.F1FS.app.dto.ResponseSimpleDriverDTO;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.service.ConstructorDriverRelationService;
import kr.co.F1FS.app.service.DriverService;
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
    private final ConstructorDriverRelationService relationService;

    @PostMapping("/save")
    @Operation(summary = "드라이버 생성", description = "드라이버를 생성하고 컨스트럭터 소속을 저장한다.")
    public ResponseEntity<Driver> save(@RequestBody CombinedDriverRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.save(request));
    }

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

    @GetMapping("/find/name")
    @Operation(summary = "드라이버 검색(Name)", description = "특정 name의 드라이버들을 검색한다.")
    public ResponseEntity<List<ResponseSimpleDriverDTO>> findByName(@RequestParam String search,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(driverService.findByNameList(search, page, size).getContent());
    }

    @PutMapping("/modify/racing-class/{id}/{racingClass}")
    @Operation(summary = "드라이버 소속 클래스 변경", description = "특정 id의 드라이버의 racingClass를 변경한다.")
    public ResponseEntity<ResponseDriverDTO> modifyRacingClass(@PathVariable Long id, @PathVariable String racingClass){
        Driver driver = driverService.findByIdNotDTO(id);
        driverService.modifyRacingClass(driver, racingClass);
        return ResponseEntity.status(HttpStatus.OK).body(driverService.findById(id));
    }

    @PutMapping("/modify/constructor/{number}/{constructorName}")
    @Operation(summary = "드라이버 소속 컨스트럭터 변경", description = "특정 드라이버의 소속 컨스트럭터를 변경한다.")
    public ResponseEntity<Void> modifyConstructor(@PathVariable Integer number, @PathVariable String constructorName){
        relationService.transfer(number, constructorName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/fire/{driverId}")
    @Operation(summary = "드라이버 소속 삭제", description = "특정 id의 드라이버를 현재 소속팀에서 삭제한다.")
    public ResponseEntity<Void> fireDriver(@PathVariable Long driverId){
        relationService.delete(driverId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
