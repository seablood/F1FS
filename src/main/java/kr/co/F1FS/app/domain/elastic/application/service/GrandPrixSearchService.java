package kr.co.F1FS.app.domain.elastic.application.service;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.GrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.GrandPrixSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.GrandPrixSearchRepository;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixSearchDTO;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixException;
import kr.co.F1FS.app.global.util.exception.grandprix.GrandPrixExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GrandPrixSearchService implements GrandPrixSearchUseCase {
    private final DocumentMapper documentMapper;
    private final GrandPrixSearchRepository grandPrixSearchRepository;
    private final GrandPrixSearchRepoPort grandPrixSearchRepoPort;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public GrandPrixDocument save(GrandPrix grandPrix){
        GrandPrixDocument document = GrandPrixDocument.builder().grandPrix(grandPrix).build();

        return grandPrixSearchRepoPort.save(document);
    }

    @Override
    public GrandPrixDocument save(GrandPrixDocument document) {
        return grandPrixSearchRepoPort.save(document);
    }

    @Override
    public GrandPrixDocument findById(Long id) {
        return grandPrixSearchRepoPort.findById(id);
    }

    @Override
    public void modify(GrandPrixDocument document, GrandPrix grandPrix) {
        document.modify(grandPrix);
        grandPrixSearchRepoPort.save(document);
    }

    @Override
    public List<ResponseGrandPrixSearchDTO> suggestGrandPrix(String keyword){
        NativeQuery query = setQuery(keyword);
        query.setMaxResults(5);

        List<ResponseGrandPrixSearchDTO> dtoList = getList(query);
        Collections.shuffle(dtoList);

        return dtoList;
    }

    @Override
    public Page<ResponseGrandPrixSearchDTO> searchGrandPrixWithPaging(int page, int size, String condition, String keyword){
        Pageable pageable = switchCondition(page, size, condition);
        NativeQuery query = setQuery(keyword);

        List<ResponseGrandPrixSearchDTO> firstList = getList(query);

        Comparator<ResponseGrandPrixSearchDTO> comparator = getComparatorFromPageable(pageable);
        List<ResponseGrandPrixSearchDTO> sorted = firstList.stream()
                .sorted(comparator)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<ResponseGrandPrixSearchDTO> paged = sorted.subList(start, end);

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
                                .should(s -> s.term(t -> t
                                        .field("season.keyword")
                                        .value(keyword)))))
                .build();

        return query;
    }

    public List<ResponseGrandPrixSearchDTO> getList(NativeQuery query){
        SearchHits<GrandPrixDocument> grandPrixHits = elasticsearchTemplate.search(query, GrandPrixDocument.class);

        List<ResponseGrandPrixSearchDTO> dtoList = grandPrixHits.stream()
                .map(hit -> {
                    GrandPrixDocument grandPrixDocument = hit.getContent();
                    ResponseGrandPrixSearchDTO dto = documentMapper.toResponseGrandPrixSearchDTO(grandPrixDocument);
                    return dto;
                })
                .toList();

        return dtoList;
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "korName"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "korName"));
            case "seasonASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "season"));
            case "seasonDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "season"));
            default:
                throw new GrandPrixException(GrandPrixExceptionType.GRAND_PRIX_CONDITION_ERROR);
        }
    }

    public Comparator<ResponseGrandPrixSearchDTO> getComparatorFromPageable(Pageable pageable){
        if(pageable.getSort().isEmpty()){
            return Comparator.comparing(ResponseGrandPrixSearchDTO::getKorName);
        }

        Sort.Order order = pageable.getSort().iterator().next();
        String property = order.getProperty();
        boolean ascending = order.getDirection().isAscending();

        return switch (property){
            case "korName" -> ascending
                    ? Comparator.comparing(ResponseGrandPrixSearchDTO::getKorName)
                    : Comparator.comparing(ResponseGrandPrixSearchDTO::getKorName).reversed();
            case "season" -> ascending
                    ? Comparator.comparing(ResponseGrandPrixSearchDTO::getSeason)
                    : Comparator.comparing(ResponseGrandPrixSearchDTO::getSeason).reversed();
            default -> Comparator.comparing(ResponseGrandPrixSearchDTO::getKorName);
        };
    }
}
