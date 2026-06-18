package kr.co.F1FS.app.domain.elastic.application.service.postRoom;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.PostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostRoomSearchRepository;
import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDocumentDTO;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ApplicationPostRoomSearchService implements PostRoomSearchUseCase {
    private final DocumentMapper documentMapper;
    private final PostRoomSearchRepository postRoomSearchRepository;
    private final PostRoomSearchRepoPort postRoomSearchRepoPort;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<ResponsePostRoomDocumentDTO> getPostRoomList(int page, int size, String condition, String keyword) {
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setQueryRoomTitleOrDescription(keyword, pageable, condition);

        return getPageImpl(query, pageable, keyword);
    }

    @Override
    public Page<ResponsePostRoomDocumentDTO> getPostRoomListByMasterUser(int page, int size, String condition, String keyword) {
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setQueryMasterUser(keyword, pageable, condition);

        return getPageImpl(query, pageable, keyword);
    }

    public NativeQuery setQueryRoomTitleOrDescription(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("roomTitle.kor^2", "roomTitle.eng^2", "roomTitle.ngram", "description.kor", "description.eng")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new":
                            s.field(f -> f.field("createTime").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostRoomException(PostRoomExceptionType.CONDITION_ERROR_POST_ROOM);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public NativeQuery setQueryMasterUser(String keyword, Pageable pageable, String condition){
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("masterUser.normalized")
                                            .value(normalized)
                                            .boost(6.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("masterUser.kor")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("masterUser.eng")
                                            .query(normalized)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                                return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("masterUser.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));

                            return b;
                        }))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new":
                            s.field(f -> f.field("createTime").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new PostRoomException(PostRoomExceptionType.CONDITION_ERROR_POST_ROOM);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable, String keyword){
        SearchHits<PostRoomDocument> hits = elasticsearchTemplate.search(query, PostRoomDocument.class);
        List<ResponsePostRoomDocumentDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponsePostRoomDocumentDTO(hit.getContent()))
                .toList();

        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        return new PageImpl(list, pageable, hits.getTotalHits());
    }
}
