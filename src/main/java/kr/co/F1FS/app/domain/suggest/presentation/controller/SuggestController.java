package kr.co.F1FS.app.domain.suggest.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.suggest.application.service.SuggestService;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.domain.suggest.presentation.dto.CreateSuggestDTO;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ModifySuggestDTO;
import kr.co.F1FS.app.global.presentation.dto.suggest.ResponseSuggestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/suggest")
@Tag(name = "Suggest(건의 사항) 컨트롤러", description = "건의 사항 관련 서비스")
public class SuggestController {
    private final SuggestService suggestService;

    @PostMapping("/save")
    @Operation(summary = "건의 사항 게시", description = "서비스 관련 건의 사항을 작성 후 게시")
    public ResponseEntity<ResponseSuggestDTO> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                   @Valid @RequestBody CreateSuggestDTO dto){
        ResponseSuggestDTO suggestDTO = suggestService.save(principalDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(suggestDTO);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "건의 사항 상세 페이지", description = "특정 ID의 건의 사항 반환")
    public ResponseEntity<ResponseSuggestDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(suggestService.getSuggestById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "작성한 모든 건의 사항 검색", description = "로그인 유저가 작성한 모든 건의 사항을 반환")
    public ResponseEntity<List<ResponseSuggestDTO>> findByUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size){
        Page<ResponseSuggestDTO> newPage = suggestService.getSuggestByUser(page, size, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "건의 사항 수정", description = "특정 건의 사항 수정")
    public ResponseEntity<ResponseSuggestDTO> modify(@PathVariable Long id, @Valid @RequestBody ModifySuggestDTO dto,
                                                     @AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.status(HttpStatus.OK).body(suggestService.modify(id, dto, principalDetails.getUser()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "건의 사항 삭제", description = "특정 건의 사항 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        suggestService.delete(id, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
