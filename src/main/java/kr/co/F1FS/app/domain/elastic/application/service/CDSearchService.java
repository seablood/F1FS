package kr.co.F1FS.app.domain.elastic.application.service;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.CDSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.CDSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CDSearchService implements CDSearchUseCase {
    private final DocumentMapper documentMapper;
    private final ConstructorSearchRepository constructorSearchRepository;
    private final DriverSearchRepository driverSearchRepository;
    private final CDSearchRepoPort cdSearchRepoPort;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public ConstructorDocument save(Constructor constructor){
        ConstructorDocument constructorDocument = ConstructorDocument.builder()
                .constructor(constructor).build();

        return cdSearchRepoPort.save(constructorDocument);
    }

    @Override
    public ConstructorDocument save(ConstructorDocument document) {
        return cdSearchRepoPort.save(document);
    }

    public DriverDocument save(Driver driver){
        DriverDocument driverDocument = DriverDocument.builder()
                .driver(driver).build();

        return cdSearchRepoPort.save(driverDocument);
    }

    @Override
    public DriverDocument save(DriverDocument document) {
        return cdSearchRepoPort.save(document);
    }

    @Override
    public ConstructorDocument findConstructorDocumentById(Long id) {
        return cdSearchRepoPort.findConstructorDocumentById(id);
    }

    @Override
    public DriverDocument findDriverDocumentById(Long id) {
        return cdSearchRepoPort.findDriverDocumentById(id);
    }

    @Override
    public void modify(DriverDocument document, Driver driver) {
        document.modify(driver);
        cdSearchRepoPort.save(document);
    }

    @Override
    public void modify(ConstructorDocument document, Constructor constructor) {
        document.modify(constructor);
        cdSearchRepoPort.save(document);
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
                    CDSearchSuggestionDTO dto = documentMapper.toCDSearchSuggestionDTO(document);
                    return dto;
                })
                .toList();
        List<CDSearchSuggestionDTO> constructorResult = constructorHits.stream()
                .map(hit -> {
                    ConstructorDocument document = hit.getContent();
                    CDSearchSuggestionDTO dto = documentMapper.toCDSearchSuggestionDTO(document);
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
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }

    private Comparator<CDSearchSuggestionDTO> getComparatorFromPageable(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return Comparator.comparing(CDSearchSuggestionDTO::getKorName); // 기본값
        }

        Sort.Order order = pageable.getSort().iterator().next(); // 첫 번째 정렬 방식 사용
        String property = order.getProperty(); // 정렬 필드
        boolean ascending = order.getDirection().isAscending(); // 정렬 오름차순 여부

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
