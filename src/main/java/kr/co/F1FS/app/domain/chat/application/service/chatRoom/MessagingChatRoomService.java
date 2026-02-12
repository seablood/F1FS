package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.port.in.subscribe.AddAndRemoveChatSubscribeUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.MessagingChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.chatRoom.ChatRoomJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.QueryChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.UpdateChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessagingChatRoomService implements MessagingChatRoomUseCase {
    private final ChatRoomDomainService chatRoomDomainService;
    private final UpdateChatRoomSearchUseCase updateChatRoomSearchUseCase;
    private final QueryChatRoomSearchUseCase queryChatRoomSearchUseCase;
    private final AddAndRemoveChatSubscribeUseCase addAndRemoveChatSubscribeUseCase;
    private final ChatRoomJpaPort chatRoomJpaPort;

    @Override
    @Transactional
    public void enterChatRoom(ChatRoom chatRoom, String username, LocalDateTime lastEnterTime) {
        ChatRoomDocument document = queryChatRoomSearchUseCase.findById(chatRoom.getId());

        chatRoomDomainService.increaseMember(chatRoom);
        updateChatRoomSearchUseCase.increaseMemberCount(document);
        chatRoomDomainService.updateLastChatMessage(chatRoom, lastEnterTime);
        chatRoomJpaPort.saveAndFlush(chatRoom);

        addAndRemoveChatSubscribeUseCase.addSubscriber(chatRoom.getId(), username, lastEnterTime);
    }

    @Override
    @Transactional
    public void sendMessage(ChatRoom chatRoom, LocalDateTime sendTime) {
        chatRoomDomainService.updateLastChatMessage(chatRoom, sendTime);
        chatRoomJpaPort.saveAndFlush(chatRoom);
    }

    @Override
    @Transactional
    public void leaveChatRoom(ChatRoom chatRoom, String username, LocalDateTime sendTime) {
        ChatRoomDocument document = queryChatRoomSearchUseCase.findById(chatRoom.getId());

        updateChatRoomSearchUseCase.decreaseMemberCount(document);
        chatRoomDomainService.decreaseMember(chatRoom);
        chatRoomDomainService.updateLastChatMessage(chatRoom, sendTime);
        chatRoomJpaPort.saveAndFlush(chatRoom);

        addAndRemoveChatSubscribeUseCase.removeSubscriber(chatRoom.getId(), username);
    }
}
