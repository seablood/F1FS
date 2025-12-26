package kr.co.F1FS.app.domain.follow.presentation.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.follow.application.port.in.user.FollowUserUseCase;
import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
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
    private final FollowUserUseCase followUserUseCase;

    @PostMapping("/toggle/{nickname}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 유저를 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable String nickname,
                                       @AuthenticationPrincipal PrincipalDetails details){
        followUserUseCase.toggle(details.getUser(), nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/follower")
    @Operation(summary = "팔로워 확인 기능(로그인)", description = "로그인 유저의 팔로워를 확인")
    public ResponseEntity<List<ResponseFollowUserDTO>> getFollowerAuth(@AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followUserUseCase.findFollowersAuth(details.getUser()));
    }

    @GetMapping("/followee")
    @Operation(summary = "팔로잉 확인 기능(로그인)", description = "로그인 유저의 팔로잉을 확인")
    public ResponseEntity<List<ResponseFollowUserDTO>> getFolloweeAuth(@AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(followUserUseCase.findFolloweesAuth(details.getUser()));
    }

    @GetMapping("/follower/not-auth/{nickname}")
    @Operation(summary = "팔로워 확인 기능", description = "특정 유저의 팔로워를 확인")
    public ResponseEntity<List<ResponseFollowUserDTO>> getFollower(@PathVariable String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(followUserUseCase.findFollowers(nickname));
    }

    @GetMapping("/followee/not-auth/{nickname}")
    @Operation(summary = "팔로잉 확인 기능", description = "특정 유저의 팔로잉을 확인")
    public ResponseEntity<List<ResponseFollowUserDTO>> getFollowee(@PathVariable String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(followUserUseCase.findFollowees(nickname));
    }
}
