package kr.co.F1FS.app.domain.chat.application.service.chatMessage;

import kr.co.F1FS.app.domain.chat.application.mapper.chatMessage.ChatMessageMapper;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.QueryChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.subscribe.CheckChatSubscribeUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.subscribe.FindChatSubscribeUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.ChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.CreateChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.QueryChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.MessagingChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.chatMessage.CreateChatMessageDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationChatMessageService implements ChatMessageUseCase {
    private final CreateChatMessageUseCase createChatMessageUseCase;
    private final QueryChatMessageUseCase queryChatMessageUseCase;
    private final FindChatSubscribeUseCase findChatSubscribeUseCase;
    private final CheckChatSubscribeUseCase checkChatSubscribeUseCase;
    private final QueryChatRoomUseCase queryChatRoomUseCase;
    private final MessagingChatRoomUseCase messagingChatRoomUseCase;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    @Transactional
    public ResponseChatMessageDTO enterChatRoom(Long roomId, CreateChatMessageDTO dto, String username) {
        dto.setContent(username + "님이 입장하였습니다.");
        if(!checkChatSubscribeUseCase.isSubscribe(roomId, username)){
            ChatMessage chatMessage = createChatMessageUseCase.save(roomId, dto, username);
            ChatRoom chatRoom = queryChatRoomUseCase.findById(roomId);
            messagingChatRoomUseCase.enterChatRoom(chatRoom, username, chatMessage.getSendTime());

            return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
        }

        return null;
    }

    @Override
    @Transactional
    public ResponseChatMessageDTO sendMessage(Long roomId, CreateChatMessageDTO dto, String username) {
        ChatMessage chatMessage = createChatMessageUseCase.save(roomId, dto, username);
        ChatRoom chatRoom = queryChatRoomUseCase.findById(roomId);
        messagingChatRoomUseCase.sendMessage(chatRoom, chatMessage.getSendTime());

        return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
    }

    @Override
    @Transactional
    public ResponseChatMessageDTO leaveChatRoom(Long roomId, CreateChatMessageDTO dto, String username) {
        dto.setContent(username + "님이 나갔습니다.");
        ChatMessage chatMessage = createChatMessageUseCase.save(roomId, dto, username);
        ChatRoom chatRoom = queryChatRoomUseCase.findById(roomId);
        messagingChatRoomUseCase.leaveChatRoom(chatRoom, username, chatMessage.getSendTime());

        return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
    }

    @Override
    public List<ResponseChatMessageDTO> getMessageList(Long roomId, String username) {
        String lastEnterTimeStr = findChatSubscribeUseCase.getLastEnterTime(roomId, username);
        LocalDateTime lastEnterTime = lastEnterTimeStr != null ? LocalDateTime.parse(lastEnterTimeStr) : LocalDateTime.MIN;

        return queryChatMessageUseCase.findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAscForDTO(roomId, lastEnterTime);
    }
}
