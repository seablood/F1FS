package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.dto.ResponseUserDTO;
import kr.co.F1FS.app.service.FollowUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow-user")
@Tag(name = "유저 팔로우 시스템", description = "유저 팔로우(즐겨찾기) 관련 기능")
public class FollowUserController {
    private final FollowUserService followUserService;

    @PostMapping("/toggle/{nickname}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 유저를 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable String nickname,
                                       @AuthenticationPrincipal PrincipalDetails details){
        followUserService.toggle(details.getUser(), nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/follower")
    @Operation(summary = "팔로워 확인 기능(로그인)", description = "로그인 유저의 팔로워를 확인")
    public ResponseEntity<List<ResponseUserDTO>> getFollowerAuth(@AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followUserService.findFollowersAuth(details.getUser()));
    }

    @GetMapping("/followee")
    @Operation(summary = "팔로잉 확인 기능(로그인)", description = "로그인 유저의 팔로잉을 확인")
    public ResponseEntity<List<ResponseUserDTO>> getFolloweeAuth(@AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followUserService.findFolloweesAuth(details.getUser()));
    }

    @GetMapping("/follower/not-auth/{nickname}")
    @Operation(summary = "팔로워 확인 기능", description = "특정 유저의 팔로워를 확인")
    public ResponseEntity<List<ResponseUserDTO>> getFollower(@PathVariable String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(followUserService.findFollowers(nickname));
    }

    @GetMapping("/followee/not-auth/{nickname}")
    @Operation(summary = "팔로잉 확인 기능", description = "특정 유저의 팔로잉을 확인")
    public ResponseEntity<List<ResponseUserDTO>> getFollowee(@PathVariable String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(followUserService.findFollowees(nickname));
    }
}
