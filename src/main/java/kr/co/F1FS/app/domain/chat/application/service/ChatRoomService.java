package kr.co.F1FS.app.domain.chat.application.service;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatRoomMapper;
import kr.co.F1FS.app.domain.chat.application.port.in.ChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.infrastructure.repository.ChatRoomRepository;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import kr.co.F1FS.app.domain.elastic.application.port.in.ChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomException;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomUseCase {
    private final ChatSubscribeService chatSubscribeService;
    private final ChatRoomSearchUseCase searchUseCase;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    @Transactional
    public void save(CreateChatRoomDTO dto, String masterUser){
        ChatRoom chatRoom = chatRoomMapper.toChatRoom(dto, masterUser);

        chatRoomRepository.save(chatRoom);
        searchUseCase.save(chatRoom);
    }

    @Override
    public Page<ResponseChatRoomDTO> findAll(int page, int size, String condition) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return chatRoomRepository.findAll(pageable).map(chatRoom -> chatRoomMapper.toResponseChatRoomDTO(chatRoom));
    }

    @Override
    public Page<ResponseChatRoomDTO> findSubscribeChatRoom(int page, int size, String condition, String username) {
        Pageable pageable = conditionSwitch(page, size, condition);
        List<Long> roomIds = chatSubscribeService.findSubscribeChatRoom(username);

        return chatRoomRepository.findByIdIn(roomIds, pageable)
                .map(chatRoom -> chatRoomMapper.toResponseChatRoomDTO(chatRoom));
    }

    @Override
    public boolean enterChatRoom(Long roomId, String username, LocalDateTime lastEnterTime) {
        if(!chatSubscribeService.isSubscribe(roomId, username)){
            ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                    .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));
            ChatRoomDocument document = searchUseCase.findById(roomId);
            chatRoom.increaseMember();
            searchUseCase.increaseMemberCount(document);
            chatRoom.updateLastChatMessage(lastEnterTime);

            chatSubscribeService.addSubscriber(roomId, username, lastEnterTime);
            chatRoomRepository.saveAndFlush(chatRoom);
            searchUseCase.save(document);

            return true;
        }else {
            return false;
        }
    }

    @Override
    public void sendMessage(Long roomId, LocalDateTime sendTime) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));
        chatRoom.updateLastChatMessage(sendTime);

        chatRoomRepository.saveAndFlush(chatRoom);
    }

    @Override
    public void leaveChatRoom(Long roomId, String username, LocalDateTime sendTime) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));
        ChatRoomDocument document = searchUseCase.findById(roomId);
        searchUseCase.decreaseMemberCount(document);
        chatRoom.decreaseMember();
        chatRoom.updateLastChatMessage(sendTime);

        chatSubscribeService.removeSubscriber(roomId, username);
        chatRoomRepository.saveAndFlush(chatRoom);
        searchUseCase.save(document);
    }

    @Override
    @Transactional
    public void modify(Long roomId, ModifyChatRoomDTO dto, String username){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));
        ChatRoomDocument document = searchUseCase.findById(roomId);

        if(!AuthorCertification.certification(username, chatRoom.getMasterUser())){
            throw new ChatRoomException(ChatRoomExceptionType.NOT_AUTHORITY_UPDATE_CHAT_ROOM);
        }
        chatRoom.modify(dto);
        searchUseCase.modify(document, chatRoom);

        chatRoomRepository.saveAndFlush(chatRoom);
        searchUseCase.save(document);
    }

    @Override
    @Transactional
    public void delete(Long roomId, String username) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));

        if(!AuthorCertification.certification(username, chatRoom.getMasterUser())){
            throw new ChatRoomException(ChatRoomExceptionType.NOT_AUTHORITY_UPDATE_CHAT_ROOM);
        }
        chatSubscribeService.deleteChatRoom(roomId);

        chatRoomRepository.delete(chatRoom);
    }

    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            case "hot" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "memberCount"));
            case "recent" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastChatMessage"));
            default:
                throw new ChatRoomException(ChatRoomExceptionType.CONDITION_ERROR_CHAT_ROOM);
        }
    }
}
