package kr.co.F1FS.app.domain.elastic.application.service.suggest;

import co.elastic.clients.elasticsearch._types.SortOrder;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.SuggestKeywordSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestListRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.SuggestKeywordSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.SuggestKeywordDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.SuggestKeywordSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ApplicationSuggestKeywordSearchService implements SuggestKeywordSearchUseCase {
    private final SuggestKeywordSearchRepository suggestKeywordSearchRepository;
    private final SuggestKeywordSearchRepoPort suggestKeywordSearchRepoPort;
    private final SaveSuggestListRedisUseCase saveSuggestListRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<String> getAutoKeywordList(String keyword) {
        if(saveSuggestListRedisUseCase.hasKey(keyword)){
            return saveSuggestListRedisUseCase.getSuggestList(keyword);
        }
        if(keyword == null || keyword.length() < 2) return List.of();

        NativeQuery query = setSuggestQuery(keyword);
        SearchHits<SuggestKeywordDocument> hits = elasticsearchTemplate.search(query, SuggestKeywordDocument.class);
        if(hits.isEmpty()) return List.of();

        List<String> keywords = hits.stream()
                .map(hit -> hit.getContent())
                .map(SuggestKeywordDocument::getSuggest)
                .toList();
        saveSuggestListRedisUseCase.saveSuggestList(keyword, keywords);

        return keywords;
    }

    public NativeQuery setSuggestQuery(String keyword) {
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("suggest.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("suggest.kor_autocomplete")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("suggest.eng_autocomplete")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                                return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("suggest.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));

                            return b;
                        }))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withMaxResults(7)
                .build();

        return query;
    }
}
