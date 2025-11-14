package kr.co.F1FS.app.domain.session.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/session")
@Tag(name = "세션 컨트롤러", description = "세션 관련 기능")
public class SessionController {
    private final SessionUseCase sessionUseCase;

    @GetMapping("/find/{id}")
    @Operation(summary = "세션 상세 정보", description = "세션 결과 포함 세션 상세 정보 반환")
    public ResponseEntity<ResponseSessionDTO> getSessionById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sessionUseCase.getSessionByID(id));
    }
}
