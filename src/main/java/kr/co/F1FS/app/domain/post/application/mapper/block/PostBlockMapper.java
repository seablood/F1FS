package kr.co.F1FS.app.domain.post.application.mapper.block;

import kr.co.F1FS.app.domain.post.domain.PostBlock;
import kr.co.F1FS.app.global.util.PostBlockType;
import org.springframework.stereotype.Component;

@Component
public class PostBlockMapper {
    public PostBlock toPostBlock(String content, PostBlockType type){
        return PostBlock.builder()
                .content(content)
                .blockType(type)
                .build();
    }
}
