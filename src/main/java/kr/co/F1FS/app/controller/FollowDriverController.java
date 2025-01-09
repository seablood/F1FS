package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.service.FollowDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow-driver")
@Tag(name = "드라이버 팔로우 시스템", description = "드라이버 팔로우(즐겨찾기) 관련 기능")
public class FollowDriverController {
    private final FollowDriverService followDriverService;

    @PostMapping("/toggle/{name}")
    @Operation(summary = "팔로우 토글 기능", description = "특정 드라이버를 팔로우 추가/취소")
    public ResponseEntity<Void> toggle(@PathVariable String name, @AuthenticationPrincipal PrincipalDetails details){
        followDriverService.toggle(details.getUser().getUsername(), name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
