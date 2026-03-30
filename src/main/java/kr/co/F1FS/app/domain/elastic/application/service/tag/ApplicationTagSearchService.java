package kr.co.F1FS.app.domain.elastic.application.service.tag;

import co.elastic.clients.elasticsearch._types.SortOrder;
import kr.co.F1FS.app.domain.elastic.application.port.in.tag.redis.SaveTagSuggestListRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.tag.TagSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.TagSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.TagDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.TagSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ApplicationTagSearchService implements TagSearchUseCase {
    private final TagSearchRepository tagSearchRepository;
    private final TagSearchRepoPort tagSearchRepoPort;
    private final SaveTagSuggestListRedisUseCase saveTagSuggestListRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<String> getAutoTagList(String keyword) {
        if(saveTagSuggestListRedisUseCase.hasKey(keyword)){
            return saveTagSuggestListRedisUseCase.getSuggestList(keyword);
        }
        if(keyword == null || keyword.length() < 2) return List.of();

        NativeQuery query = setQuery(keyword);

        return getSuggestList(query, keyword);
    }

    public NativeQuery setQuery(String keyword){
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("name.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("name.autocomplete_kor")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("name.autocomplete_eng")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                                return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("name.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));

                            return b;
                        }))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withMaxResults(10)
                .build();

        return query;
    }

    public List<String> getSuggestList(NativeQuery query, String keyword){
        SearchHits<TagDocument> hits = elasticsearchTemplate.search(query, TagDocument.class);
        if(hits.isEmpty()) return List.of();

        List<String> list = hits.stream()
                .map(hit -> hit.getContent())
                .map(TagDocument::getName)
                .toList();
        saveTagSuggestListRedisUseCase.saveSuggestList(keyword, list);

        return list;
    }
}
