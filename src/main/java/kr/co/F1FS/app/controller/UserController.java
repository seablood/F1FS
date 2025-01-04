package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.dto.CreateUserDTO;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "사용자 관련 서비스")
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    @Operation(summary = "회원가입(자체 계정)", description = "자체 로그인 계정 생성")
    public ResponseEntity<User> save(@RequestBody CreateUserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }
}
