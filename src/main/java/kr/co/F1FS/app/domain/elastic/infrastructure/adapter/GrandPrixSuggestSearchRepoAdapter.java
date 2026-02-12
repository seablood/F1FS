package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSuggestSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixSuggestDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSuggestSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrandPrixSuggestSearchRepoAdapter implements GrandPrixSuggestSearchRepoPort {
    private final GrandPrixSuggestSearchRepository suggestSearchRepository;

    @Override
    public void save(GrandPrixSuggestDocument document) {
        suggestSearchRepository.save(document);
    }
}
