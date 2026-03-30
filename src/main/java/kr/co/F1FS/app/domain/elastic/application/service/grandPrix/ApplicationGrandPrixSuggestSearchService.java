package kr.co.F1FS.app.domain.elastic.application.service.grandPrix;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.GrandPrixSuggestSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.redis.SaveGrandPrixSuggestListRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSuggestSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixSuggestDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSuggestSearchRepository;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseSuggestGrandPrixSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ApplicationGrandPrixSuggestSearchService implements GrandPrixSuggestSearchUseCase {
    private final DocumentMapper documentMapper;
    private final GrandPrixSuggestSearchRepository suggestSearchRepository;
    private final GrandPrixSuggestSearchRepoPort suggestSearchRepoPort;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final SaveGrandPrixSuggestListRedisUseCase saveGrandPrixSuggestListRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<ResponseSuggestGrandPrixSearchDTO> getAutoGrandPrixList(String keyword) {
        if(saveGrandPrixSuggestListRedisUseCase.hasKey(keyword)){
            return saveGrandPrixSuggestListRedisUseCase.getSuggestList(keyword);
        }
        if(keyword == null || keyword.length() < 2) return List.of();

        NativeQuery query = setSuggestQuery(keyword);

        return getSuggestList(query, keyword);
    }

    @Override
    public Page<ResponseSuggestGrandPrixSearchDTO> getGrandPrixList(int page, int size, String condition, String keyword) {
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setPagingQuery(keyword, pageable, condition);

        return getPageImpl(query, pageable, keyword);
    }

    public NativeQuery setSuggestQuery(String keyword){
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("korName.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.prefix(p -> {
                                    p.field("engName.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("korName.kor_autocomplete")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("engName.eng_autocomplete")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                                return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("korName.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("engName.ngram")
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

    public NativeQuery setPagingQuery(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(keyword)
                                .fields("korName", "korName.ngram", "engName", "engName.ngram")
                                .operator(Operator.Or)))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "nameASC" :
                            s.field(f -> f.field("korName.keyword").order(SortOrder.Asc));
                            return s;
                        case "nameDESC" :
                            s.field(f -> f.field("korName.keyword").order(SortOrder.Desc));
                            return s;
                        default:
                            s.field(f -> f.field("korName.keyword").order(SortOrder.Asc));
                            return s;
                    }
                })
                .build();

        return query;
    }

    public List<ResponseSuggestGrandPrixSearchDTO> getSuggestList(NativeQuery query, String keyword){
        SearchHits<GrandPrixSuggestDocument> hits = elasticsearchTemplate.search(query, GrandPrixSuggestDocument.class);
        if(hits.isEmpty()) return List.of();

        List<ResponseSuggestGrandPrixSearchDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponseSuggestGrandPrixSearchDTO(hit.getContent()))
                .toList();
        saveGrandPrixSuggestListRedisUseCase.saveSuggestList(keyword, list);

        return list;
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable, String keyword){
        SearchHits<GrandPrixSuggestDocument> hits = elasticsearchTemplate.search(query, GrandPrixSuggestDocument.class);
        List<ResponseSuggestGrandPrixSearchDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponseSuggestGrandPrixSearchDTO(hit.getContent()))
                .toList();

        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        return new PageImpl(list, pageable, hits.getTotalHits());
    }
}
