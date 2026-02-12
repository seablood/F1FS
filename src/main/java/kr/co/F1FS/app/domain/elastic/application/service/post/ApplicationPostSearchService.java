package kr.co.F1FS.app.domain.elastic.application.service.post;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.post.PostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.domain.elastic.presentation.dto.TagListRequestDTO;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationPostSearchService implements PostSearchUseCase {
    private final DocumentMapper documentMapper;
    private final PostSearchRepository postSearchRepository;
    private final PostSearchRepoPort postSearchRepoPort;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public NativeQuery setQueryTitle(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(keyword)
                                .fields("title.kor", "title.eng")
                                .operator(Operator.Or)))
                .build();

        return query;
    }

    public NativeQuery setQueryContent(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(keyword)
                                .fields("content.kor", "content.eng")
                                .operator(Operator.Or)))
                .build();

        return query;
    }

    public NativeQuery setQueryTitleOrContent(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("title.kor^3", "title.eng^3", "content.kor", "content.eng")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryAuthor(String keyword){
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("author.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("author.kor")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("author.eng")
                                            .query(normalized)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                                return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("author.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));

                            return b;
                        }))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryTags(String keyword){
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("tags.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("tags.kor")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("tags.eng")
                                            .query(normalized)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                                return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("tags.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));

                            return b;
                        }))
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryTagsFilter(List<String> tags){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .terms(t -> t
                                                .field("tags.keyword")
                                                .terms(v -> v.value(
                                                        tags.stream()
                                                                .map(FieldValue::of)
                                                                .toList()
                                                ))))))
                .build();

        return query;
    }

    @Override
    public Page<ResponsePostDocumentDTO> getPostList(int page, int size, String condition, String option, String keyword){
        Pageable pageable = switchCondition(page, size, condition);

        switch (option){
            case "title" :
                NativeQuery query = setQueryTitle(keyword);
                return getPageImpl(query, pageable, keyword);
            case "content" :
                NativeQuery query2 = setQueryContent(keyword);
                return getPageImpl(query2, pageable, keyword);
            case "titleOrContent" :
                NativeQuery query3 = setQueryTitleOrContent(keyword);
                return getPageImpl(query3, pageable, keyword);
            case "author" :
                if (keyword.length() < 3) return new PageImpl<>(List.of());
                NativeQuery query4 = setQueryAuthor(keyword);
                return getPageImpl(query4, pageable, keyword);
            case "tags" :
                NativeQuery query5 = setQueryTags(keyword);
                return getPageImpl(query5, pageable, keyword);
            default:
                throw new PostException(PostExceptionType.SEARCH_ERROR_POST);
        }
    }

    @Override
    public Page<ResponsePostDocumentDTO> getPostListByTags(int page, int size, String condition, TagListRequestDTO dto) {
        Pageable pageable = switchCondition(page, size, condition);
        NativeQuery query = setQueryTagsFilter(dto.getTags());

        return getPageImpl(query, pageable, null);
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            case "like" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeNum"));
            default:
                throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
        }
    }

    public Comparator<PostDocument> getComparatorFromPageable(Pageable pageable){
        if(pageable.getSort().isEmpty()){
            return Comparator.comparing(PostDocument::getCreatedAt); // 기본값
        }

        Sort.Order order = pageable.getSort().iterator().next(); // 첫 번째 정렬 방식 사용
        String property = order.getProperty(); // 정렬 필드
        boolean ascending = order.getDirection().isAscending(); // 정렬 오름차순 여부

        return switch (property) {
            case "createdAt" -> ascending
                    ? Comparator.comparing(PostDocument::getCreatedAt)
                    : Comparator.comparing(PostDocument::getCreatedAt).reversed();
            case "likeNum" -> ascending
                    ? Comparator.comparing(PostDocument::getLikeNum)
                    : Comparator.comparing(PostDocument::getLikeNum).reversed();
            default -> Comparator.comparing(PostDocument::getCreatedAt); // fallback
        };
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable, String keyword){
        SearchHits<PostDocument> hits = elasticsearchTemplate.search(query, PostDocument.class);
        List<PostDocument> list = hits.stream()
                .map(hit -> hit.getContent())
                .toList();

        Comparator<PostDocument> comparator = getComparatorFromPageable(pageable);
        List<ResponsePostDocumentDTO> sorted = list.stream()
                .sorted(comparator)
                .map(postDocument -> documentMapper.toResponsePostDocumentDTO(postDocument))
                .toList();

        if (keyword != null) saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<ResponsePostDocumentDTO> paged = sorted.subList(start, end);

        return new PageImpl<>(paged, pageable, sorted.size());
    }
}
