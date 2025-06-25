package kr.co.F1FS.app.domain.follow.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.follow.application.service.FollowDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow-driver")
@Tag(name = "드라이버 팔로우 시스템", description = "드라이버 팔로우(즐겨찾기) 관련 기능")
public class FollowDriverController {
    private final FollowDriverService followDriverService;
    private final UserUseCase userUseCase;

    @PostMapping("/toggle/{driverId}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 드라이버를 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable Long driverId, @AuthenticationPrincipal PrincipalDetails details){
        followDriverService.toggle(details.getUser(), driverId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/following/not-auth/{nickname}")
    @Operation(summary = "팔로잉 확인 기능", description = "특정 유저의 드라이버 팔로잉 확인")
    public ResponseEntity<List<ResponseFollowDriverDTO>> getFollowing(@PathVariable String nickname){
        User user = userUseCase.findByNicknameNotDTO(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(followDriverService.getFollowingDriver(user));
    }

    @GetMapping("/following")
    @Operation(summary = "팔로잉 확인 기능(로그인)", description = "로그인 유저의 드라이버 팔로잉 확인")
    public ResponseEntity<List<ResponseFollowDriverDTO>> getFollowingDriverAuth(
                                        @AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followDriverService.getFollowingDriver(details.getUser()));
    }
}
