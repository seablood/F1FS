package kr.co.F1FS.app.presentation.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.presentation.driver.dto.SimpleResponseDriverDTO;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.application.follow.FollowDriverService;
import kr.co.F1FS.app.application.user.UserService;
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
    private final UserService userService;

    @PostMapping("/toggle/{driverId}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 드라이버를 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable Long driverId, @AuthenticationPrincipal PrincipalDetails details){
        followDriverService.toggle(details.getUser(), driverId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/following/not-auth/{nickname}")
    @Operation(summary = "팔로잉 확인 기능", description = "특정 유저의 드라이버 팔로잉 확인")
    public ResponseEntity<List<SimpleResponseDriverDTO>> getFollowing(@PathVariable String nickname){
        User user = userService.findByNicknameNotDTO(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(followDriverService.getFollowingDriver(user));
    }

    @GetMapping("/following")
    @Operation(summary = "팔로잉 확인 기능(로그인)", description = "로그인 유저의 드라이버 팔로잉 확인")
    public ResponseEntity<List<SimpleResponseDriverDTO>> getFollowingDriverAuth(
                                        @AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followDriverService.getFollowingDriver(details.getUser()));
    }
}
