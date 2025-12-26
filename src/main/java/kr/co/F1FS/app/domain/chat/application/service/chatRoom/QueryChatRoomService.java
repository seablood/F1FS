package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatRoomMapper;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.QueryChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatRoomJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryChatRoomService implements QueryChatRoomUseCase {
    private final ChatRoomJpaPort chatRoomJpaPort;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public ChatRoom findById(Long roomId) {
        return chatRoomJpaPort.findById(roomId);
    }

    @Override
    public Page<ResponseChatRoomDTO> findAll(Pageable pageable) {
        return chatRoomJpaPort.findAll(pageable).map(chatRoom -> chatRoomMapper.toResponseChatRoomDTO(chatRoom));
    }

    @Override
    public Page<ResponseChatRoomDTO> findByIdIn(List<Long> roomIds, Pageable pageable) {
        return chatRoomJpaPort.findByIdIn(roomIds, pageable)
                .map(chatRoom -> chatRoomMapper.toResponseChatRoomDTO(chatRoom));
    }
}
