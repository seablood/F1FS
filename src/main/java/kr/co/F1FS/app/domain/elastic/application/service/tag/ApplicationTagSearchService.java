package kr.co.F1FS.app.domain.elastic.application.service.tag;

import co.elastic.clients.elasticsearch._types.SortOrder;
import kr.co.F1FS.app.domain.elastic.application.port.in.tag.TagSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.TagSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.TagDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.TagSearchRepository;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ApplicationTagSearchService implements TagSearchUseCase {
    private final TagSearchRepository tagSearchRepository;
    private final TagSearchRepoPort tagSearchRepoPort;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final RedisHandler redisHandler;

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

    @Override
    public List<String> getAutoTagList(String keyword) {
        if(redisHandler.getStringListRedisTemplate().hasKey("tag-suggest:"+keyword)){
            return redisHandler.getStringListRedisTemplate().opsForList().range("tag-suggest:"+keyword, 0, -1);
        }
        if(keyword == null || keyword.length() < 2) return List.of();

        NativeQuery query = setQuery(keyword);
        SearchHits<TagDocument> hits = elasticsearchTemplate.search(query, TagDocument.class);
        if (hits.isEmpty()) return List.of();

        List<String> tags = hits.stream()
                .map(hit -> hit.getContent())
                .map(TagDocument::getName)
                .toList();
        redisHandler.getStringListRedisTemplate().opsForList().rightPushAll("tag-suggest:"+keyword, tags);
        redisHandler.getStringListRedisTemplate().expire("tag-suggest:"+keyword, Duration.ofMinutes(5));

        return tags;
    }
}
