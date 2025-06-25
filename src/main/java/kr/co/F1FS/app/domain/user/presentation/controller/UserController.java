package kr.co.F1FS.app.domain.user.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.domain.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "사용자 관련 서비스")
public class UserController {
    private final UserService userService;

    // 가장 기본적인 정보들만 반환 중 -> 추후 유저 정보에 변화가 있을 시, 같이 fix
    @GetMapping("/profile")
    @Operation(summary = "유저 정보(로그인된 계정)", description = "로그인된 계정의 정보를 반환")
    public ResponseEntity<ResponseUserDTO> getProfile(@AuthenticationPrincipal PrincipalDetails details){
        ResponseUserDTO userDTO = ResponseUserDTO.toDto(details.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/user-info")
    @Operation(summary = "유저 정보", description = "특정 유저의 정보를 반환")
    public ResponseEntity<ResponseUserDTO> getUserInfo(@RequestParam(value = "nickname") String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByNickname(nickname));
    }

    @PutMapping("/modify-nickname")
    @Operation(summary = "유저 닉네임 변경", description = "로그인한 유저의 닉네임 변경")
    public ResponseEntity<ResponseUserDTO> modify(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @Valid @RequestBody ModifyNicknameDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.modify(principalDetails.getUser(), dto));
    }
}
