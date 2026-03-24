package kr.co.F1FS.app.domain.post.presentation.dto;

import kr.co.F1FS.app.global.util.PostBlockType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostBlockRequestDTO {
    private String title;
    private List<BlockRequest> blocks = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    @Getter
    public static class BlockRequest {
        private String content;
        private PostBlockType type;
    }
}
