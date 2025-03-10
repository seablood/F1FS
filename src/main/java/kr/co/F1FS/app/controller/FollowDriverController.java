package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.dto.SimpleResponseDriverDTO;
import kr.co.F1FS.app.service.FollowDriverService;
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

    @PostMapping("/toggle/{name}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 드라이버를 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable String name, @AuthenticationPrincipal PrincipalDetails details){
        followDriverService.toggle(details.getUser(), name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/following/not-auth/{nickname}")
    @Operation(summary = "팔로잉 확인 기능", description = "특정 유저의 드라이버 팔로잉 확인")
    public ResponseEntity<List<SimpleResponseDriverDTO>> getFollowing(@PathVariable String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(followDriverService.getFollowingDriver(nickname));
    }

    @GetMapping("/following")
    @Operation(summary = "팔로잉 확인 기능(로그인)", description = "로그인 유저의 드라이버 팔로잉 확인")
    public ResponseEntity<List<SimpleResponseDriverDTO>> getFollowingDriverAuth(
                                        @AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followDriverService.getFollowingDriverAuth(details.getUser()));
    }
}
