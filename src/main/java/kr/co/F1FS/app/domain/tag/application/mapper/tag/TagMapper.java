package kr.co.F1FS.app.domain.tag.application.mapper.tag;

import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.global.presentation.dto.tag.ResponseTagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag toTag(String name){
        return Tag.builder().name(name).build();
    }

    public ResponseTagDTO toResponseTagDTO(Tag tag){
        return ResponseTagDTO.builder()
                .name(tag.getName())
                .build();
    }
}
