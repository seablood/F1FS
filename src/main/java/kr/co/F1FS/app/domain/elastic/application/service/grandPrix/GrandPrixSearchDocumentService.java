package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrandPrixSearchDocumentService {
    private final DocumentMapper documentMapper;

    public GrandPrixDocument createEntity(GrandPrix grandPrix){
        return documentMapper.toGrandPrixDocument(grandPrix);
    }

    public void modify(GrandPrixDocument document, GrandPrix grandPrix){
        document.modify(grandPrix);
    }
}
