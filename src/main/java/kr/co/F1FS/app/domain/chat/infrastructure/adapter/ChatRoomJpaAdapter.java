package kr.co.F1FS.app.domain.chat.infrastructure.adapter;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatRoomMapper;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatRoomJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.infrastructure.repository.ChatRoomRepository;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomException;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomJpaAdapter implements ChatRoomJpaPort {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void saveAndFlush(ChatRoom chatRoom) {
        chatRoomRepository.saveAndFlush(chatRoom);
    }

    @Override
    public Page<ChatRoom> findAll(Pageable pageable) {
        return chatRoomRepository.findAll(pageable);
    }

    @Override
    public ChatRoom findById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));
    }

    @Override
    public Page<ChatRoom> findByIdIn(List<Long> roomIds, Pageable pageable) {
        return chatRoomRepository.findByIdIn(roomIds, pageable);
    }

    @Override
    public void delete(ChatRoom chatRoom) {
        chatRoomRepository.delete(chatRoom);
    }
}
