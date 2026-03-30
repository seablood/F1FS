package kr.co.F1FS.app.domain.elastic.application.service.user;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.UserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.redis.SaveUserSuggestListRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.UserSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.UserSearchRepository;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDocumentDTO;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
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
public class ApplicationUserSearchService implements UserSearchUseCase {
    private final UserSearchRepository userSearchRepository;
    private final UserSearchRepoPort userSearchRepoPort;
    private final SaveUserSuggestListRedisUseCase saveUserSuggestListRedisUseCase;
    private final DocumentMapper documentMapper;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<ResponseUserDocumentDTO> suggestUser(String keyword) {
        if(saveUserSuggestListRedisUseCase.hasKey(keyword)){
            return saveUserSuggestListRedisUseCase.getSuggestList(keyword);
        }
        if(keyword == null || keyword.length() < 2) return List.of();

        NativeQuery query = setSuggestQuery(keyword);

        return getSuggestList(query, keyword);
    }

    @Override
    public Page<ResponseUserDocumentDTO> searchUserWithPaging(int page, int size, String condition, String keyword) {
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setQuery(keyword, pageable, condition);

        return getPageImpl(query, pageable);
    }

    public NativeQuery setSuggestQuery(String keyword){
        String normalized = keyword.trim().toLowerCase(Locale.ENGLISH);
        String trimmed = keyword.trim();

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> {
                            b.must(m -> m.bool(bb -> {
                                bb.should(s -> s.prefix(p -> {
                                    p.field("username.normalized")
                                            .value(normalized)
                                            .boost(5.0f);
                                    return p;
                                }));
                                bb.should(s -> s.prefix(p -> {
                                    p.field("nickname.normalized")
                                            .value(normalized)
                                            .boost(5.0f);
                                    return p;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("username.eng_autocomplete")
                                            .query(normalized)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("nickname.kor_autocomplete")
                                            .query(trimmed)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.should(s -> s.match(mm -> {
                                    mm.field("nickname.eng_autocomplete")
                                            .query(normalized)
                                            .boost(4.0f);
                                    return mm;
                                }));
                                bb.minimumShouldMatch("1");

                               return bb;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("username.ngram")
                                        .query(normalized)
                                        .boost(1.5f);
                                return m;
                            }));
                            b.should(s -> s.match(m -> {
                                m.field("nickname.ngram")
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

    public NativeQuery setQuery(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("nickname.kor^3", "nickname.eng^3", "nickname.ngram", "username")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withPageable(pageable)
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .withSort(s -> {
                    switch (condition){
                        case "nicknameASC" :
                            s.field(f -> f.field("nickname.keyword").order(SortOrder.Asc));
                            return s;
                        case "nicknameDESC" :
                            s.field(f -> f.field("nickname.keyword").order(SortOrder.Desc));
                            return s;
                        case "followerNum" :
                            s.field(f -> f.field("followerNum").order(SortOrder.Asc));
                            return s;
                        default:
                            throw new UserException(UserExceptionType.SEARCH_ERROR_USER);
                    }
                })
                .build();

        return query;
    }

    public List<ResponseUserDocumentDTO> getSuggestList(NativeQuery query, String keyword){
        SearchHits<UserDocument> hits = elasticsearchTemplate.search(query, UserDocument.class);
        if(hits.isEmpty()) return List.of();

        List<ResponseUserDocumentDTO> list = hits.stream()
                .map(hit -> {
                    UserDocument document = hit.getContent();
                    ResponseUserDocumentDTO dto = documentMapper.toResponseUserDocumentDTO(document);
                    return dto;
                })
                .toList();
        saveUserSuggestListRedisUseCase.saveSuggestList(keyword, list);

        return list;
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable){
        SearchHits<UserDocument> hits = elasticsearchTemplate.search(query, UserDocument.class);
        List<ResponseUserDocumentDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponseUserDocumentDTO(hit.getContent()))
                .toList();

        return new PageImpl(list, pageable, hits.getTotalHits());
    }
}
