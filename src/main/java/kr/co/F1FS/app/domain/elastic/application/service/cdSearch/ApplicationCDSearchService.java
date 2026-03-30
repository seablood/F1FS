package kr.co.F1FS.app.domain.elastic.application.service.cdSearch;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.CDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.cdSearch.redis.SaveCDSuggestListRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.CDSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ConstructorSearchRepository;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationCDSearchService implements CDSearchUseCase {
    private final DocumentMapper documentMapper;
    private final ConstructorSearchRepository constructorSearchRepository;
    private final DriverSearchRepository driverSearchRepository;
    private final CDSearchRepoPort cdSearchRepoPort;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final SaveCDSuggestListRedisUseCase saveCDSuggestListRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<CDSearchSuggestionDTO> suggestCD(String keyword){
        if (saveCDSuggestListRedisUseCase.hasKey(keyword)){
            return saveCDSuggestListRedisUseCase.getSuggestList(keyword);
        }

        NativeQuery query = setSuggestQuery(keyword);

        List<CDSearchSuggestionDTO> combine = getCombineList(query);

        saveCDSuggestListRedisUseCase.saveSuggestList(keyword, combine);

        return combine;
    }

    @Override
    public Page<CDSearchSuggestionDTO> searchCDWithPaging(int page, int size, String condition, String keyword){
        Pageable pageable = PageRequest.of(page, size);

        int fetchSize = page * size + size;

        NativeQuery query = setQuery(keyword, condition, fetchSize);

        List<CDSearchSuggestionDTO> combine = getCombineList(query);

        sortCombineList(combine, condition);
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), combine.size());
        List<CDSearchSuggestionDTO> paged = combine.subList(start, end);

        return new PageImpl<>(paged, pageable, combine.size());
    }

    public NativeQuery setQuery(String keyword, String condition, int fetchSize){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("korName^3", "engName^3", "racingClass")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withMaxResults(fetchSize)
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withSort(s -> {
                    switch (condition){
                        case "nameASC" :
                            s.field(f -> f.field("korName.keyword").order(SortOrder.Asc));
                            return s;
                        case "nameDESC" :
                            s.field(f -> f.field("korName.keyword").order(SortOrder.Desc));
                            return s;
                        case "racingClass" :
                            s.field(f -> f.field("racingClass.keyword").order(SortOrder.Asc));
                            return s;
                        default:
                            throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
                    }
                })
                .build();

        return query;
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
                                            .boost(5.0f);
                                    return p;
                                }));
                                bb.should(s -> s.prefix(p -> {
                                    p.field("engName.normalized")
                                            .value(normalized)
                                            .boost(5.0f);
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
                                            .query(normalized)
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
                .withMaxResults(7)
                .build();

        return query;
    }

    public List<CDSearchSuggestionDTO> getCombineList(NativeQuery query){
        SearchHits<DriverDocument> driverHits = elasticsearchTemplate.search(query, DriverDocument.class);
        SearchHits<ConstructorDocument> constructorHits = elasticsearchTemplate.search(query, ConstructorDocument.class);

        List<CDSearchSuggestionDTO> driverResult = driverHits.stream()
                .map(hit -> {
                    DriverDocument document = hit.getContent();
                    CDSearchSuggestionDTO dto = documentMapper.toCDSearchSuggestionDTO(document, hit.getScore());
                    return dto;
                })
                .toList();
        List<CDSearchSuggestionDTO> constructorResult = constructorHits.stream()
                .map(hit -> {
                    ConstructorDocument document = hit.getContent();
                    CDSearchSuggestionDTO dto = documentMapper.toCDSearchSuggestionDTO(document, hit.getScore());
                    return dto;
                })
                .toList();

        List<CDSearchSuggestionDTO> combine = new ArrayList<>(driverHits.getSearchHits().size() + constructorHits.getSearchHits().size());
        combine.addAll(driverResult);
        combine.addAll(constructorResult);

        return combine;
    }

    public void sortCombineList(List<CDSearchSuggestionDTO> list, String condition){
        Comparator<CDSearchSuggestionDTO> comparator =
                Comparator.comparing(CDSearchSuggestionDTO::getScore).reversed();

        switch (condition){
            case "nameASC" :
                comparator = comparator.thenComparing(CDSearchSuggestionDTO::getKorName);
                break;
            case "nameDESC" :
                comparator = comparator.thenComparing(
                        CDSearchSuggestionDTO::getKorName,
                        Comparator.reverseOrder()
                );
                break;
            case "racingClass" :
                comparator = comparator.thenComparing(CDSearchSuggestionDTO::getRacingClass);
                break;
        }

        list.sort(comparator);
    }
}
