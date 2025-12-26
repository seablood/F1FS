package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.ChatRoomSearchUseCase;
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

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationChatRoomSearchService implements ChatRoomSearchUseCase {
    private final DocumentMapper documentMapper;
    private final ChatRoomSearchRepository chatRoomSearchRepository;
    private final ChatRoomSearchRepoPort chatRoomSearchRepoPort;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<ResponseChatRoomDocumentDTO> getChatRoomList(int page, int size, String condition, String keyword) {
        Pageable pageable = switchCondition(page, size, condition);
        NativeQuery query = setQueryNameOrDescription(keyword);

        return getPageImpl(query, pageable);
    }

    public NativeQuery setQueryNameOrDescription(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m
                                        .field("name.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("name.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("description.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("description.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .build();

        return query;
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            case "hot" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "memberCount"));
            default:
                throw new ChatRoomException(ChatRoomExceptionType.CONDITION_ERROR_CHAT_ROOM);
        }
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable){
        SearchHits<ChatRoomDocument> hits = elasticsearchTemplate.search(query, ChatRoomDocument.class);
        List<ChatRoomDocument> list = hits.stream()
                .map(hit -> hit.getContent())
                .toList();

        Comparator<ChatRoomDocument> comparator = getComparatorFromPageable(pageable);
        List<ResponseChatRoomDocumentDTO> sorted = list.stream()
                .sorted(comparator)
                .map(chatRoomDocument -> documentMapper.toResponseChatRoomDocumentDTO(chatRoomDocument))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<ResponseChatRoomDocumentDTO> paged = sorted.subList(start, end);

        return new PageImpl(paged, pageable, sorted.size());
    }

    public Comparator<ChatRoomDocument> getComparatorFromPageable(Pageable pageable){
        if(pageable.getSort().isEmpty()){
            return Comparator.comparing(ChatRoomDocument::getCreateTime); // 기본값
        }

        Sort.Order order = pageable.getSort().iterator().next(); // 첫 번째 정렬 방식 사용
        String property = order.getProperty(); // 정렬 필드
        boolean ascending = order.getDirection().isAscending(); // 정렬 오름차순 여부

        return switch (property) {
            case "createTime" -> ascending
                    ? Comparator.comparing(ChatRoomDocument::getCreateTime)
                    : Comparator.comparing(ChatRoomDocument::getCreateTime).reversed();
            case "memberCount" -> ascending
                    ? Comparator.comparing(ChatRoomDocument::getMemberCount)
                    : Comparator.comparing(ChatRoomDocument::getMemberCount).reversed();
            default -> Comparator.comparing(ChatRoomDocument::getCreateTime); // fallback
        };
    }
}
