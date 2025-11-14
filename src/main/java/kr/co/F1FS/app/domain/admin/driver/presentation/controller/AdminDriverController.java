package kr.co.F1FS.app.domain.admin.driver.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.driver.application.port.in.AdminDriverUseCase;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.ModifyDriverDTO;
import kr.co.F1FS.app.domain.team.application.port.in.ConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/driver")
@Tag(name = "드라이버 컨트롤러(관리자 권한)", description = "드라이버 관련 서비스(관리자 권한)")
public class AdminDriverController {
    private final AdminDriverUseCase adminDriverUseCase;
    private final ConstructorDriverRelationUseCase relationUseCase;

    @PostMapping("/save")
    @Operation(summary = "드라이버 생성", description = "드라이버를 생성하고 컨스트럭터 소속을 저장한다.")
    public ResponseEntity<AdminResponseDriverDTO> save(@Valid @RequestBody CombinedDriverRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDriverUseCase.save(request));
    }

    @GetMapping("/find-all")
    @Operation(summary = "드라이버 전체 리스트", description = "드라이버 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseDriverDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                 @RequestParam(value = "condition", defaultValue = "nameASC") String condition){
        Page<SimpleResponseDriverDTO> newPage = adminDriverUseCase.findAll(page, size, condition);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "드라이버 상세 정보", description = "특정 ID 드라이버 상세 정보 반환")
    public ResponseEntity<AdminResponseDriverDTO> getDriverById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminDriverUseCase.getDriverById(id));
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "드라이버 수정", description = "특정 드라이버 정보 수정")
    public ResponseEntity<AdminResponseDriverDTO> modify(@PathVariable Long id,
                                                         @Valid @RequestBody ModifyDriverDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(adminDriverUseCase.modify(id, dto));
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
