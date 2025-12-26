package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.port.in.ChatSubscribeUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.MessagingChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatRoomJpaPort;
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
    private final ChatSubscribeUseCase chatSubscribeUseCase;
    private final ChatRoomDomainService chatRoomDomainService;
    private final UpdateChatRoomSearchUseCase updateChatRoomSearchUseCase;
    private final QueryChatRoomSearchUseCase queryChatRoomSearchUseCase;
    private final ChatRoomJpaPort chatRoomJpaPort;

    @Override
    @Transactional
    public boolean enterChatRoom(Long roomId, String username, LocalDateTime lastEnterTime) {
        if(!chatSubscribeUseCase.isSubscribe(roomId, username)){
            ChatRoom chatRoom = chatRoomJpaPort.findById(roomId);
            ChatRoomDocument document = queryChatRoomSearchUseCase.findById(roomId);

            chatRoomDomainService.increaseMember(chatRoom);
            updateChatRoomSearchUseCase.increaseMemberCount(document);
            chatRoomDomainService.updateLastChatMessage(chatRoom, lastEnterTime);
            chatRoomJpaPort.saveAndFlush(chatRoom);

            chatSubscribeUseCase.addSubscriber(roomId, username, lastEnterTime);

            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public void sendMessage(Long roomId, LocalDateTime sendTime) {
        ChatRoom chatRoom = chatRoomJpaPort.findById(roomId);
        chatRoomDomainService.updateLastChatMessage(chatRoom, sendTime);
        chatRoomJpaPort.saveAndFlush(chatRoom);
    }

    @Override
    @Transactional
    public void leaveChatRoom(Long roomId, String username, LocalDateTime sendTime) {
        ChatRoom chatRoom = chatRoomJpaPort.findById(roomId);
        ChatRoomDocument document = queryChatRoomSearchUseCase.findById(roomId);

        updateChatRoomSearchUseCase.decreaseMemberCount(document);
        chatRoomDomainService.decreaseMember(chatRoom);
        chatRoomDomainService.updateLastChatMessage(chatRoom, sendTime);
        chatRoomJpaPort.saveAndFlush(chatRoom);

        chatSubscribeUseCase.removeSubscriber(roomId, username);
    }
}
