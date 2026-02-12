package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixSuggestDocument;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrandPrixSuggestSearchDocumentService {
    private final DocumentMapper documentMapper;

    public GrandPrixSuggestDocument createEntity(GrandPrix grandPrix){
        return documentMapper.toGrandPrixSuggestDocument(grandPrix);
    }
}
