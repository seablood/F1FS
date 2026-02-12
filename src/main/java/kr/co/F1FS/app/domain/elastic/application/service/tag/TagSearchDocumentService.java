package kr.co.F1FS.app.domain.elastic.application.service.tag;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.TagDocument;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagSearchDocumentService {
    private final DocumentMapper documentMapper;

    public TagDocument createEntity(Tag tag){
        return documentMapper.toTagDocument(tag);
    }
}
