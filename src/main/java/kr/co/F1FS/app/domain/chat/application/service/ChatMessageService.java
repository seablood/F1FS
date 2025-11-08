package kr.co.F1FS.app.domain.chat.application.service;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatMessageMapper;
import kr.co.F1FS.app.domain.chat.application.port.in.ChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatMessageJpaPort;
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
public class ChatMessageService implements ChatMessageUseCase {
    private final ChatSubscribeService chatSubscribeService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageJpaPort chatMessageJpaPort;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    @Transactional
    public ResponseChatMessageDTO enterChatRoom(Long roomId, CreateChatMessageDTO dto, String username) {
        dto.setContent(username + "님이 입장하였습니다.");
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(roomId, dto, username);
        if(chatRoomService.enterChatRoom(roomId, username, chatMessage.getSendTime())){
            chatMessageJpaPort.save(chatMessage);

            return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
        }

        return null;
    }

    @Override
    @Transactional
    public ResponseChatMessageDTO sendMessage(Long roomId, CreateChatMessageDTO dto, String username) {
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(roomId, dto, username);
        chatRoomService.sendMessage(roomId, chatMessage.getSendTime());

        chatMessageJpaPort.save(chatMessage);
        return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
    }

    @Override
    @Transactional
    public ResponseChatMessageDTO leaveChatRoom(Long roomId, CreateChatMessageDTO dto, String username) {
        dto.setContent(username + "님이 나갔습니다.");
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(roomId, dto, username);
        chatRoomService.leaveChatRoom(roomId, username, chatMessage.getSendTime());

        chatMessageJpaPort.save(chatMessage);
        return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
    }

    @Override
    public List<ResponseChatMessageDTO> getMessageList(Long roomId, String username) {
        String lastEnterTimeStr = chatSubscribeService.getLastEnterTime(roomId, username);
        LocalDateTime lastEnterTime = lastEnterTimeStr != null ? LocalDateTime.parse(lastEnterTimeStr) : LocalDateTime.MIN;

        return chatMessageJpaPort.findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(roomId, lastEnterTime);
    }
}
