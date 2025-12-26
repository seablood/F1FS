package kr.co.F1FS.app.domain.chat.presentation.controller;

import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.ChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatMessageDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageUseCase chatMessageUseCase;
    private final SimpMessagingTemplate template;

    @MessageMapping("/enter/{roomId}")
    public void enterChatRoom(@DestinationVariable Long roomId, @Payload CreateChatMessageDTO dto,
                              SimpMessageHeaderAccessor accessor){
        ResponseChatMessageDTO messageDTO = chatMessageUseCase.enterChatRoom(roomId, dto,
                accessor.getSessionAttributes().get("AUTHENTICATED_USERNAME").toString());

        if(messageDTO != null){
            template.convertAndSend("/topic/" + roomId, messageDTO);
        }
    }

    @MessageMapping("/send/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload CreateChatMessageDTO dto,
                            SimpMessageHeaderAccessor accessor){
        ResponseChatMessageDTO messageDTO = chatMessageUseCase.sendMessage(roomId, dto,
                accessor.getSessionAttributes().get("AUTHENTICATED_USERNAME").toString());

        template.convertAndSend("/topic/" + roomId, messageDTO);
    }

    @MessageMapping("/leave/{roomId}")
    public void leaveChatRoom(@DestinationVariable Long roomId, @Payload CreateChatMessageDTO dto,
                              SimpMessageHeaderAccessor accessor){
        ResponseChatMessageDTO messageDTO = chatMessageUseCase.leaveChatRoom(roomId, dto,
                accessor.getSessionAttributes().get("AUTHENTICATED_USERNAME").toString());

        template.convertAndSend("/topic/" + roomId, messageDTO);
    }
}
