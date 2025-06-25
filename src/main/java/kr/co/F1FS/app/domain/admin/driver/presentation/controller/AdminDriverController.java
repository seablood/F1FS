package kr.co.F1FS.app.domain.admin.driver.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.driver.application.service.AdminDriverService;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/driver")
@Tag(name = "드라이버 컨트롤러(관리자 권한)", description = "드라이버 관련 서비스(관리자 권한)")
public class AdminDriverController {
    private final AdminDriverService adminDriverService;
    private final ConstructorDriverRelationUseCase relationUseCase;

    @PostMapping("/save")
    @Operation(summary = "드라이버 생성", description = "드라이버를 생성하고 컨스트럭터 소속을 저장한다.")
    public ResponseEntity<AdminResponseDriverDTO> save(@Valid @RequestBody CombinedDriverRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDriverService.save(request));
    }

    @PutMapping("/modify/constructor/{number}/{constructorName}")
    @Operation(summary = "드라이버 소속 컨스트럭터 변경", description = "특정 드라이버의 소속 컨스트럭터를 변경한다.")
    public ResponseEntity<Void> modifyConstructor(@PathVariable Integer number, @PathVariable String constructorName){
        relationUseCase.transfer(number, constructorName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/fire")
    @Operation(summary = "드라이버 소속 삭제", description = "특정 id의 드라이버를 옵션에 따라 소속팀에서 삭제한다.")
    public ResponseEntity<Void> fireDriver(@RequestParam(value = "driverId", defaultValue = "0") Long driverId,
                                           @RequestParam(value = "option", defaultValue = "currentTeam") String option){
        relationUseCase.delete(driverId, option);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
