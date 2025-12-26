package kr.co.F1FS.app.domain.complain.user.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.complain.user.application.port.in.UserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-complain")
@Tag(name = "유저 신고 컨트롤러", description = "사용자 신고 관련 서비스")
public class UserComplainController {
    private final UserComplainUseCase userComplainUseCase;

    @PostMapping("/save")
    @Operation(summary = "유저 신고", description = "특정 유저를 신고")
    public ResponseEntity<Void> userComplain(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @Valid @RequestBody CreateUserComplainDTO dto){
        userComplainUseCase.userComplain(principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-list")
    @Operation(summary = "신고 목록", description = "유저 신고 목록 반환")
    public ResponseEntity<List<ResponseUserComplainDTO>> getUserComplainList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                                             @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponseUserComplainDTO> newPage = userComplainUseCase.getUserComplainListByFromUser(
                page, size, condition, principalDetails.getUser()
        );
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "유저 신고 내용", description = "유저 신고 세부 내용 보기")
    public ResponseEntity<ResponseUserComplainDTO> getUserComplain(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userComplainUseCase.getUserComplain(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "유저 신고 삭제", description = "특정 유저 신고 삭제")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id){
        userComplainUseCase.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
