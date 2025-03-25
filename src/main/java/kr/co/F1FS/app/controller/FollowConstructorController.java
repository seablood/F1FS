package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.dto.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.service.FollowConstructorService;
import kr.co.F1FS.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow-constructor")
@Tag(name = "컨스트럭터 팔로우 시스템", description = "컨스트럭터 팔로우(즐겨찾기) 관련 기능")
public class FollowConstructorController {
    private final FollowConstructorService followConstructorService;
    private final UserService userService;

    @PostMapping("/toggle/{constructorId}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 컨스트럭터 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable Long constructorId, @AuthenticationPrincipal PrincipalDetails details){
        followConstructorService.toggle(details.getUser(), constructorId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/following/not-auth/{nickname}")
    @Operation(summary = "팔로잉 확인 기능", description = "특정 유저의 컨스트럭터 팔로잉 확인")
    public ResponseEntity<List<SimpleResponseConstructorDTO>> getFollowing(@PathVariable String nickname){
        User user = userService.findByNicknameNotDTO(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(followConstructorService.getFollowingConstructor(user));
    }

    @GetMapping("/following")
    @Operation(summary = "팔로잉 확인 기능", description = "로그인 유저의 컨스트럭터 팔로잉 확인")
    public ResponseEntity<List<SimpleResponseConstructorDTO>> getFollowingConstructorAuth(
                                        @AuthenticationPrincipal PrincipalDetails details){
        return ResponseEntity.status(HttpStatus.OK).body(
                followConstructorService.getFollowingConstructor(details.getUser()));
    }
}
