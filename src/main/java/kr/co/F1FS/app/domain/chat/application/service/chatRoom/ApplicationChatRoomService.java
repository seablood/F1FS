package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.port.in.*;
import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.*;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.DeleteChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.QueryChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.UpdateChatRoomSearchUseCase;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationChatRoomService implements ChatRoomUseCase {
    private final ChatSubscribeUseCase chatSubscribeUseCase;
    private final CreateChatRoomUseCase createChatRoomUseCase;
    private final UpdateChatRoomUseCase updateChatRoomUseCase;
    private final DeleteChatRoomUseCase deleteChatRoomUseCase;
    private final QueryChatRoomUseCase queryChatRoomUseCase;
    private final UpdateChatRoomSearchUseCase updateChatRoomSearchUseCase;
    private final QueryChatRoomSearchUseCase queryChatRoomSearchUseCase;
    private final DeleteChatRoomSearchUseCase deleteChatRoomSearchUseCase;

    @Override
    @Transactional
    public void save(CreateChatRoomDTO dto, String masterUser){
        createChatRoomUseCase.createEntity(dto, masterUser);
    }

    @Override
    public Page<ResponseChatRoomDTO> findAll(int page, int size, String condition) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryChatRoomUseCase.findAll(pageable);
    }

    @Override
    public Page<ResponseChatRoomDTO> findSubscribeChatRoom(int page, int size, String condition, String username) {
        Pageable pageable = conditionSwitch(page, size, condition);
        List<Long> roomIds = chatSubscribeUseCase.findSubscribeChatRoom(username);

        return queryChatRoomUseCase.findByIdIn(roomIds, pageable);
    }

    @Override
    @Transactional
    public void modify(Long roomId, ModifyChatRoomDTO dto, String username){
        ChatRoom chatRoom = queryChatRoomUseCase.findById(roomId);
        ChatRoomDocument document = queryChatRoomSearchUseCase.findById(roomId);

        if(!AuthorCertification.certification(username, chatRoom.getMasterUser())){
            throw new ChatRoomException(ChatRoomExceptionType.NOT_AUTHORITY_UPDATE_CHAT_ROOM);
        }
        updateChatRoomUseCase.modify(chatRoom ,dto);
        updateChatRoomSearchUseCase.modify(document, chatRoom);
    }

    @Override
    @Transactional
    public void delete(Long roomId, String username) {
        ChatRoom chatRoom = queryChatRoomUseCase.findById(roomId);
        ChatRoomDocument document = queryChatRoomSearchUseCase.findById(roomId);

        if(!AuthorCertification.certification(username, chatRoom.getMasterUser())){
            throw new ChatRoomException(ChatRoomExceptionType.NOT_AUTHORITY_UPDATE_CHAT_ROOM);
        }
        chatSubscribeUseCase.deleteChatRoom(roomId);

        deleteChatRoomUseCase.delete(chatRoom);
        deleteChatRoomSearchUseCase.delete(document);
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
