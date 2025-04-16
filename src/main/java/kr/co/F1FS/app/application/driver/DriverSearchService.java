package kr.co.F1FS.app.application.driver;

import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.repository.elastic.DriverSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverSearchService {
    private final DriverSearchRepository driverSearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public void save(Driver driver){
        DriverDocument driverDocument = DriverDocument.builder()
                .driver(driver).build();

        driverSearchRepository.save(driverDocument);
    }

    public List<DriverDocument> suggestDrivers(String keyword){
        NativeQuery query = setQuery(keyword);

        SearchHits<DriverDocument> hits = elasticsearchTemplate.search(query, DriverDocument.class);

        return hits.stream()
                .map(hit -> hit.getContent())
                .toList();
    }

    public Page<DriverDocument> searchDriversWithPaging(int page, int size, String condition, String keyword){
        Pageable pageable = switchCondition(page, size, condition);

        NativeQuery query = setQuery(keyword);

        SearchHits<DriverDocument> hits = elasticsearchTemplate.search(query, DriverDocument.class);
        List<DriverDocument> list = hits.stream()
                .map(hit -> hit.getContent())
                .toList();

        return new PageImpl<>(list, pageable, hits.getTotalHits());
    }

    private NativeQuery setQuery(String keyword){
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
                                        .field("team")
                                        .query(keyword)))
                                .should(s -> s.match(m -> m
                                        .field("team.ngram_search")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.matchPhrasePrefix(mp -> mp
                                        .field("engTeam")
                                        .query(keyword)))
                                .should(s -> s.match(m -> m
                                        .field("engTeam")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .withMaxResults(10)
                .build();

        return query;
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "korName"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "korName"));
            case "teamASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "team"));
            case "teamDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "team"));
            default :
                throw new RuntimeException("condition 오류");
        }
    }
}
