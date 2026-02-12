package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.SuggestKeywordSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.SuggestKeywordDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.SuggestKeywordSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuggestKeywordSearchRepoAdapter implements SuggestKeywordSearchRepoPort {
    private final SuggestKeywordSearchRepository suggestKeywordSearchRepository;

    @Override
    public void save(SuggestKeywordDocument document) {
        suggestKeywordSearchRepository.save(document);
    }

    @Override
    public void saveAll(List<SuggestKeywordDocument> list) {
        suggestKeywordSearchRepository.saveAll(list);
    }
}
