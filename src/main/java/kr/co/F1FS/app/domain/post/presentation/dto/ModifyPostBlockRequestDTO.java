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
public class ModifyPostBlockRequestDTO {
    private String title;
    private List<BlockRequest> blocks = new ArrayList<>();
    private List<String> addTags;
    private List<String> deleteTags;

    @Getter
    public static class BlockRequest {
        private String content;
        private PostBlockType type;
    }
}
