package kr.co.F1FS.app.domain.chat.application.service.chatMessage;

import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.ChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.ChatSubscribeUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.CreateChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.QueryChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.MessagingChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatMessageDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationChatMessageService implements ChatMessageUseCase {
    private final ChatMessageDomainService chatMessageDomainService;
    private final CreateChatMessageUseCase createChatMessageUseCase;
    private final QueryChatMessageUseCase queryChatMessageUseCase;
    private final ChatSubscribeUseCase chatSubscribeUseCase;
    private final MessagingChatRoomUseCase messagingChatRoomUseCase;

    @Override
    @Transactional
    public ResponseChatMessageDTO enterChatRoom(Long roomId, CreateChatMessageDTO dto, String username) {
        dto.setContent(username + "님이 입장하였습니다.");
        ChatMessage chatMessage = chatMessageDomainService.createEntity(roomId, dto, username);
        if(messagingChatRoomUseCase.enterChatRoom(roomId, username, chatMessage.getSendTime())){
            return createChatMessageUseCase.save(chatMessage);
        }

        return null;
    }

    @Override
    @Transactional
    public ResponseChatMessageDTO sendMessage(Long roomId, CreateChatMessageDTO dto, String username) {
        ChatMessage chatMessage = chatMessageDomainService.createEntity(roomId, dto, username);
        messagingChatRoomUseCase.sendMessage(roomId, chatMessage.getSendTime());

        return createChatMessageUseCase.save(chatMessage);
    }

    @Override
    @Transactional
    public ResponseChatMessageDTO leaveChatRoom(Long roomId, CreateChatMessageDTO dto, String username) {
        dto.setContent(username + "님이 나갔습니다.");
        ChatMessage chatMessage = chatMessageDomainService.createEntity(roomId, dto, username);
        messagingChatRoomUseCase.leaveChatRoom(roomId, username, chatMessage.getSendTime());

        return createChatMessageUseCase.save(chatMessage);
    }

    @Override
    public List<ResponseChatMessageDTO> getMessageList(Long roomId, String username) {
        String lastEnterTimeStr = chatSubscribeUseCase.getLastEnterTime(roomId, username);
        LocalDateTime lastEnterTime = lastEnterTimeStr != null ? LocalDateTime.parse(lastEnterTimeStr) : LocalDateTime.MIN;

        return queryChatMessageUseCase.findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(roomId, lastEnterTime);
    }
}
