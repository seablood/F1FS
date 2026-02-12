package kr.co.F1FS.app.domain.tag.application.service.tag;

import kr.co.F1FS.app.domain.tag.application.mapper.tag.TagMapper;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.global.application.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagDomainService {
    private final TagMapper tagMapper;
    private final ValidationService validationService;

    public Tag createEntity(String name){
        Tag tag = tagMapper.toTag(name);
        validationService.checkValid(tag);

        return tag;
    }
}
