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

    public NativeQuery setQueryTitle(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(keyword)
                                .fields("title.kor", "title.eng", "title.ngram")
                                .operator(Operator.Or)))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Desc));
                            return s;
                        case "older" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Asc));
                            return s;
                        case "like" :
                            s.field(f -> f.field("likeNum").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    }
                })
                .build();

        return query;
    }

    public NativeQuery setQueryContent(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(keyword)
                                .fields("content.kor", "content.eng", "content.ngram")
                                .operator(Operator.Or)))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Desc));
                            return s;
                        case "older" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Asc));
                            return s;
                        case "like" :
                            s.field(f -> f.field("likeNum").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    }
                })
                .build();

        return query;
    }

    public NativeQuery setQueryTitleOrContent(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("title.kor^3", "title.eng^3", "title.ngram^2", "content.kor", "content.eng")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Desc));
                            return s;
                        case "older" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Asc));
                            return s;
                        case "like" :
                            s.field(f -> f.field("likeNum").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryAuthor(String keyword, Pageable pageable, String condition){
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
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Desc));
                            return s;
                        case "older" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Asc));
                            return s;
                        case "like" :
                            s.field(f -> f.field("likeNum").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryTags(String keyword, Pageable pageable, String condition){
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
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Desc));
                            return s;
                        case "older" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Asc));
                            return s;
                        case "like" :
                            s.field(f -> f.field("likeNum").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryTagsFilter(List<String> tags, Pageable pageable, String condition){
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
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Desc));
                            return s;
                        case "older" :
                            s.field(f -> f.field("createdAt").order(SortOrder.Asc));
                            return s;
                        case "like" :
                            s.field(f -> f.field("likeNum").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    }
                })
                .build();

        return query;
    }

    @Override
    public Page<ResponsePostDocumentDTO> getPostList(int page, int size, String condition, String option, String keyword){
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);

        switch (option){
            case "title" :
                NativeQuery query = setQueryTitle(keyword, pageable, condition);
                return getPageImpl(query, pageable, keyword);
            case "content" :
                NativeQuery query2 = setQueryContent(keyword, pageable, condition);
                return getPageImpl(query2, pageable, keyword);
            case "titleOrContent" :
                NativeQuery query3 = setQueryTitleOrContent(keyword, pageable, condition);
                return getPageImpl(query3, pageable, keyword);
            case "author" :
                if (keyword.length() < 3) return new PageImpl<>(List.of());
                NativeQuery query4 = setQueryAuthor(keyword, pageable, condition);
                return getPageImpl(query4, pageable, keyword);
            case "tags" :
                NativeQuery query5 = setQueryTags(keyword, pageable, condition);
                return getPageImpl(query5, pageable, keyword);
            default:
                throw new PostException(PostExceptionType.SEARCH_ERROR_POST);
        }
    }

    @Override
    public Page<ResponsePostDocumentDTO> getPostListByTags(int page, int size, String condition, TagListRequestDTO dto) {
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setQueryTagsFilter(dto.getTags(), pageable, condition);

        return getPageImpl(query, pageable, null);
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable, String keyword){
        SearchHits<PostDocument> hits = elasticsearchTemplate.search(query, PostDocument.class);
        List<ResponsePostDocumentDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponsePostDocumentDTO(hit.getContent()))
                .toList();

        if (keyword != null) saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        return new PageImpl<>(list, pageable, hits.getTotalHits());
    }
}
