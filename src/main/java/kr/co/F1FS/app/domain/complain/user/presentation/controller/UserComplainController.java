package kr.co.F1FS.app.domain.complain.user.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.complain.user.application.service.UserComplainService;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-complain")
public class UserComplainController {
    private final UserComplainService userComplainService;

    @PostMapping("/save")
    @Operation(summary = "유저 신고", description = "특정 유저를 신고")
    public ResponseEntity<Void> userComplain(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @Valid @RequestBody CreateUserComplainDTO dto){
        userComplainService.userComplain(principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
