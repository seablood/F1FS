package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.ChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.ChatRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ChatRoomSearchRepository;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDocumentDTO;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomException;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomExceptionType;
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
public class ApplicationChatRoomSearchService implements ChatRoomSearchUseCase {
    private final DocumentMapper documentMapper;
    private final ChatRoomSearchRepository chatRoomSearchRepository;
    private final ChatRoomSearchRepoPort chatRoomSearchRepoPort;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<ResponseChatRoomDocumentDTO> getChatRoomList(int page, int size, String condition, String keyword) {
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setQueryNameOrDescription(keyword, pageable, condition);

        return getPageImpl(query, pageable, keyword);
    }

    public NativeQuery setQueryNameOrDescription(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("name.kor^2", "name.eng^2", "name.ngram", "description.kor", "description.eng")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new":
                            s.field(f -> f.field("createTime").order(SortOrder.Desc));
                            return s;
                        case "hot":
                            s.field(f -> f.field("memberCount").order(SortOrder.Desc));
                            return s;
                        default:
                            throw new ChatRoomException(ChatRoomExceptionType.CONDITION_ERROR_CHAT_ROOM);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable, String keyword){
        SearchHits<ChatRoomDocument> hits = elasticsearchTemplate.search(query, ChatRoomDocument.class);
        List<ResponseChatRoomDocumentDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponseChatRoomDocumentDTO(hit.getContent()))
                .toList();

        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        return new PageImpl(list, pageable, hits.getTotalHits());
    }
}
