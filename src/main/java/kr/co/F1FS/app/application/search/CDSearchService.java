package kr.co.F1FS.app.application.search;

import kr.co.F1FS.app.domain.model.elastic.ConstructorDocument;
import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import kr.co.F1FS.app.domain.model.rdb.Constructor;
import kr.co.F1FS.app.domain.repository.elastic.ConstructorSearchRepository;
import kr.co.F1FS.app.presentation.search.dto.CDSearchSuggestionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CDSearchService {
    private final ConstructorSearchRepository constructorSearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public void save(Constructor constructor){
        ConstructorDocument constructorDocument = ConstructorDocument.builder()
                .constructor(constructor).build();

        constructorSearchRepository.save(constructorDocument);
    }

    public List<CDSearchSuggestionDTO> suggestCD(String keyword){
        NativeQuery query = setQuery(keyword);
        query.setMaxResults(5);

        List<CDSearchSuggestionDTO> combine = getCombineList(query);
        Collections.shuffle(combine);

        return combine;
    }

    public Page<CDSearchSuggestionDTO> searchCDWithPaging(int page, int size, String condition, String keyword){
        Pageable pageable = switchCondition(page, size, condition);
        NativeQuery query = setQuery(keyword);

        List<CDSearchSuggestionDTO> combine = getCombineList(query);

        Comparator<CDSearchSuggestionDTO> comparator = getComparatorFromPageable(pageable);
        List<CDSearchSuggestionDTO> sorted = combine.stream()
                .sorted(comparator)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<CDSearchSuggestionDTO> paged = sorted.subList(start, end);

        return new PageImpl<>(paged, pageable, sorted.size());
    }

    public NativeQuery setQuery(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.matchPhrasePrefix(mp -> mp
                                        .field("korName")
                                        .query(keyword)))
                                .should(s -> s.match(m -> m
                                        .field("korName.ngram_search")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.matchPhrasePrefix(mp -> mp
                                        .field("engName")
                                        .query(keyword)))
                                .should(s -> s.match(m -> m
                                        .field("engName")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.matchPhrasePrefix(mp -> mp
                                        .field("racingClass")
                                        .query(keyword)))
                                .should(s -> s.match(m -> m
                                        .field("racingClass")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .build();

        return query;
    }

    public List<CDSearchSuggestionDTO> getCombineList(NativeQuery query){
        SearchHits<DriverDocument> driverHits = elasticsearchTemplate.search(query, DriverDocument.class);
        SearchHits<ConstructorDocument> constructorHits = elasticsearchTemplate.search(query, ConstructorDocument.class);

        List<CDSearchSuggestionDTO> driverResult = driverHits.stream()
                .map(hit -> {
                    DriverDocument document = hit.getContent();
                    CDSearchSuggestionDTO dto = CDSearchSuggestionDTO.builder()
                            .id(document.getId())
                            .korName(document.getKorName())
                            .engName(document.getEngName())
                            .racingClass(document.getRacingClass())
                            .type("driver")
                            .build();
                    return dto;
                })
                .toList();
        List<CDSearchSuggestionDTO> constructorResult = constructorHits.stream()
                .map(hit -> {
                    ConstructorDocument document = hit.getContent();
                    CDSearchSuggestionDTO dto = CDSearchSuggestionDTO.builder()
                            .id(document.getId())
                            .korName(document.getKorName())
                            .engName(document.getEngName())
                            .racingClass(document.getRacingClass())
                            .type("constructor")
                            .build();
                    return dto;
                })
                .toList();

        List<CDSearchSuggestionDTO> combine = new ArrayList<>();
        combine.addAll(driverResult);
        combine.addAll(constructorResult);

        return combine;
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "korName"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "korName"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            case "type" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "type"));
            default:
                throw new RuntimeException("condition 오류");
        }
    }

    private Comparator<CDSearchSuggestionDTO> getComparatorFromPageable(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return Comparator.comparing(CDSearchSuggestionDTO::getKorName); // 기본값
        }

        Sort.Order order = pageable.getSort().iterator().next();
        String property = order.getProperty();
        boolean ascending = order.getDirection().isAscending();

        return switch (property) {
            case "korName" -> ascending
                    ? Comparator.comparing(CDSearchSuggestionDTO::getKorName)
                    : Comparator.comparing(CDSearchSuggestionDTO::getKorName).reversed();
            case "racingClass" -> ascending
                    ? Comparator.comparing(CDSearchSuggestionDTO::getRacingClass)
                    : Comparator.comparing(CDSearchSuggestionDTO::getRacingClass).reversed();
            case "type" -> ascending
                    ? Comparator.comparing(CDSearchSuggestionDTO::getType)
                    : Comparator.comparing(CDSearchSuggestionDTO::getType).reversed();
            default -> Comparator.comparing(CDSearchSuggestionDTO::getKorName); // fallback
        };
    }
}
