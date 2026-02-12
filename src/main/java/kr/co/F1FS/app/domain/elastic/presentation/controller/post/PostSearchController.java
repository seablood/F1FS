package kr.co.F1FS.app.domain.elastic.presentation.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.PostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.presentation.dto.TagListRequestDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search-post")
@Tag(name = "게시글 실시간 검색", description = "게시글을 실시간으로 검색")
public class PostSearchController {
    private final PostSearchUseCase postSearchUseCase;

    @GetMapping("/page-search")
    @Operation(summary = "게시글 검색", description = "제목, 내용, 작성자를 통해 게시 검색")
    public ResponseEntity<List<ResponsePostDocumentDTO>> pageSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                                                    @RequestParam(value = "condition", defaultValue = "new") String condition,
                                                                    @RequestParam(value = "option", defaultValue = "title") String option,
                                                                    @RequestParam(value = "search") String search){
        return ResponseEntity.status(HttpStatus.OK).body(postSearchUseCase.getPostList(
                page, size, condition, option, search).getContent());
    }

    @PostMapping("/page-search/tags")
    @Operation(summary = "게시글 검색(태그)", description = "여러 태그에 대한 게시글 검색")
    public ResponseEntity<List<ResponsePostDocumentDTO>> getPostListByTags(@RequestBody TagListRequestDTO dto,
                                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                                           @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<ResponsePostDocumentDTO> newPage = postSearchUseCase.getPostListByTags(page, size, condition, dto);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }
}
