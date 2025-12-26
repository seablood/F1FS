package kr.co.F1FS.app.domain.team.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.team.application.service.admin.ApplicationAdminConstructorDriverRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/team")
@Tag(name = "컨스트럭터-드라이버 팀 관리 컨트롤러(관리자 권한)", description = "컨스트럭터-드라이버 팀 관리 관련 서비스(관리자 권한)")
public class AdminConstructorDriverRelationController {
    private final ApplicationAdminConstructorDriverRelationService adminRelationService;

    @PutMapping("/transfer/{number}/{constructorName}")
    @Operation(summary = "드라이버 소속 컨스트럭터 변경", description = "특정 드라이버의 소속 컨스트럭터를 변경한다.")
    public ResponseEntity<Void> transfer(@PathVariable Integer number, @PathVariable String constructorName){
        adminRelationService.transfer(number, constructorName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/fire")
    @Operation(summary = "드라이버 소속 삭제", description = "특정 id의 드라이버를 옵션에 따라 소속팀에서 삭제한다.")
    public ResponseEntity<Void> fireDriver(@RequestParam(value = "driverId", defaultValue = "0") Long driverId,
                                           @RequestParam(value = "option", defaultValue = "currentTeam") String option){
        adminRelationService.delete(driverId, option);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
