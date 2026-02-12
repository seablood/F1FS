package kr.co.F1FS.app.domain.elastic.application.service.suggest;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.SuggestKeywordDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SuggestKeywordSearchDocumentService {
    private final DocumentMapper documentMapper;

    public SuggestKeywordDocument createEntity(String suggest, Long searchCount, LocalDateTime lastSearchedAt){
        return documentMapper.toSuggestKeywordDocument(suggest, searchCount, lastSearchedAt);
    }
}
